package dasturlash.uz.service;

import dasturlash.uz.dto.sms.SmsAuthDTO;
import dasturlash.uz.dto.sms.SmsAuthResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class SmsSenderService {

    @Value("${sms.eskiz.mail}")
    private String email;

    @Value("${sms.eskiz.password}")
    private String password;
    @Autowired
    private RestTemplate restTemplate;

    public void sendRegistrationSms(String phone){
        // code generate
        String body = "joytop.uz partali. Ro'yxatdan o'tish uchun tasdiqlash kodi: 312312";
        sendSms(phone, body);
        // save sms code to smsHistoryEntity
    }


    // login
    private String login() {
        SmsAuthDTO post = new SmsAuthDTO();
        post.setEmail(email);
        post.setPassword(password);

        String url = "https://notify.eskiz.uz/api/auth/login";
        SmsAuthResponseDTO result = restTemplate.postForObject(url, post, SmsAuthResponseDTO.class);
        return result.getData().getToken();
    }

    // send message
    private void sendSms(String phonNumber, String message) {
        Map<String, String> body = new HashMap<>();
        body.put("mobile_phone", phonNumber);
        body.put("message", message);
        body.put("from", "4546");

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("Authorization", "Bearer " + login());

        String url = "https://notify.eskiz.uz/api/message/sms/send";
        RequestEntity<Map<String, String>> request = RequestEntity
                .post(url)
                .headers(headers)
                .body(body);

        ResponseEntity<String> response = restTemplate.exchange(request, String.class);

        System.out.println("Sms send response: status " + response.getStatusCode());
        System.out.println("Sms send response: body " + response.getBody());
    }


}
