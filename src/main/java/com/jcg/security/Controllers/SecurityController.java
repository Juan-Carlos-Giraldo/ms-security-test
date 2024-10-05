package com.jcg.security.Controllers;

import com.jcg.security.Models.User;
import com.jcg.security.Models.Role;
import com.jcg.security.Models.UserRole;
import com.jcg.security.Models.Session;
import com.jcg.security.Repositories.SessionRepository;
import com.jcg.security.Repositories.UserRepository;
import com.jcg.security.Repositories.UserRoleRepository;
import com.jcg.security.Services.EncryptionService;
import com.jcg.security.Services.JwtService;
import com.jcg.security.Services.RequestURL;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping("/api/public/security")
public class SecurityController {
    @Autowired
    private UserRepository theUserRepository;
    @Autowired
    private UserRoleRepository theUserRoleRepository;
    @Autowired
    private SessionRepository theSessionRepository;
    @Autowired
    private EncryptionService theEncryptionService;
    @Autowired
    private JwtService theJwtService;
    @Autowired
    private RequestURL theRequestURL;

    @PostMapping("send-code")
    public void sendCodeTelegram() {
        // Send code to Telegram via API http://127.0.0.1:5000/sendmessage
        /*
        * Body:
        *   "chat_id": "chat_id",
        *   "text": "Message"
        * */
        HashMap<String, String> body = new HashMap<>();
        body.put("chat_id", "7331160941");
        body.put("text", "Este es un mensaje de prueba desde SpringBoot");
        theRequestURL.sendRequest("http://127.0.0.1:5000/sendmessage", body);
    }

    @PostMapping("/login")
    public HashMap<String, Object> login(@RequestBody User theNewUser,
                                         final HttpServletResponse response) throws IOException {
        HashMap<String, Object> theResponse = new HashMap<>();
        HashMap<String, Object> tokenResponse;
        User theActualUser = this.theUserRepository.getUserByEmail(theNewUser.getEmail());
        if (theActualUser != null &&
                theActualUser.getPassword().equals(theEncryptionService.convertSHA256(theNewUser.getPassword()))) {
            List<UserRole> theUsersRoles = theUserRoleRepository.getRolesByUserId(theActualUser.get_id());
            List<Role> theRoles = theUsersRoles.stream()
                    .map(UserRole::getRole)
                    .collect(Collectors.toList());

            tokenResponse = theJwtService.generateToken(theActualUser);
            String token = tokenResponse.get("token").toString();
            Date expirationDate = (Date) tokenResponse.get("expiration");

            List<Session> theSessions = theSessionRepository.getSessionByUser(theActualUser.get_id());
            theSessions.forEach(session -> {
                session.setToken(token);
                session.setExpirationDate(expirationDate);
            });
            theSessionRepository.saveAll(theSessions);

            theActualUser.setPassword("");
            theResponse.put("token", tokenResponse.get("token").toString());
            theResponse.put("user", theActualUser);
        } else {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
        }
        return theResponse;
    }
}
