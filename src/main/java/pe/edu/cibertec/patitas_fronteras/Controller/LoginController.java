package pe.edu.cibertec.patitas_fronteras.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import pe.edu.cibertec.patitas_fronteras.viewmodel.LoginModel;
import pe.edu.cibertec.patitas_fronteras.viewmodel.LoginRequestDTO;

@Controller
@RequestMapping("/login")
public class LoginController {
    private final RestTemplate restTemplate;
    @Autowired
    public LoginController(RestTemplate restTemplate){
        this.restTemplate = restTemplate;
    }


    @GetMapping("/inicio")
    public String inicio(Model model) {
        LoginModel loginModel = new LoginModel("00", "", "","");
        model.addAttribute("loginModel", loginModel);
        return "inicio";
    }

    @PostMapping("/autenticar")
    public String autenticar(@RequestParam("tipoDocumento") String tipoDocumento,
                             @RequestParam("numeroDocumento") String numeroDocumento,
                             @RequestParam("password") String password,
                             Model model) {

        String URL = "http://localhost:8081/autenticacion/login";

        LoginRequestDTO loginRequestDTO = new LoginRequestDTO(tipoDocumento, numeroDocumento, password);

        ResponseEntity<LoginModel> loginModelResponse = restTemplate.postForEntity(URL,loginRequestDTO, LoginModel.class);

        LoginModel loginModel = loginModelResponse.getBody();


        if (loginModel.codigo().equals("00")){
            model.addAttribute("loginModel", loginModel);
            return "principal";
        }else {
            model.addAttribute("loginModel", loginModel);
            return "inicio";
        }

    }

}