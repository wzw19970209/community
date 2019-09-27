package spring_boot.demo.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import spring_boot.demo.Provider.OkHttpProvider;
import spring_boot.demo.dto.AccessDTO;
import spring_boot.demo.dto.GitHubUser;
import spring_boot.demo.mapper.UserMapper;
import spring_boot.demo.model.User;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;
import java.util.UUID;

@Controller
public class AuthController {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private OkHttpProvider okHttpProvider;
    @Value("${github.client_secret}")
    private String client_secret;
    @Value("${github.client_id}")
    private String client_id;
    @Value("${github.redirect_uri}")
    private String redirect_uri;
    @GetMapping("/callback")
    public String callback(@RequestParam String code, @RequestParam String state, Model model, HttpServletRequest request){
        AccessDTO accessDTO = new AccessDTO();
        accessDTO.setClient_id(client_id);
        accessDTO.setClient_secret(client_secret);
        accessDTO.setCode(code);
        accessDTO.setRedirect_uri(redirect_uri);
        accessDTO.setState(state);
        String string = okHttpProvider.getAccessToken(accessDTO);
        GitHubUser gitHubUser = okHttpProvider.getGitHubUser(string);
        if(gitHubUser!=null){
            User user = new User();
            user.setToken(UUID.randomUUID().toString().replaceAll("-",""));
            user.setName(gitHubUser.getName());
            user.setAccountId(String.valueOf(gitHubUser.getId()));
            user.setGmtCreate((System.currentTimeMillis()));
            user.setGmtModify(user.getGmtCreate());
            userMapper.insertUser(user);
            request.getSession().setAttribute("user",gitHubUser);
            return "redirect:/";
        }else {
            return "redirect:/";
        }
    }
}
