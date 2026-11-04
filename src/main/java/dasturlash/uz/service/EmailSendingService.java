package dasturlash.uz.service;

import dasturlash.uz.dto.response.MessageDTO;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;

@Service
public class EmailSendingService {
    @Value("${spring.mail.username}")
    private String fromAccount;
    @Autowired
    private JavaMailSender javaMailSender;

    public String sendSimpleMessage(MessageDTO dto) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setFrom(fromAccount);
        msg.setTo(dto.getToAccount());
        msg.setSubject(dto.getSubject());
        msg.setText(dto.getText());
        javaMailSender.send(msg);

        return "Mail was send";
    }

    public String sendMimeMessage(String toAccount, String title, String code) {
        String text = String.format("<!DOCTYPE html>\n" +
                "<html lang=\"uz\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "    <title>Kun.uz - Tasdiqlash kodi</title>\n" +
                "</head>\n" +
                "\n" +
                "<body style=\"margin:0; padding:0; background-color:#f4f6f8; font-family:Arial, Helvetica, sans-serif;\">\n" +
                "\n" +
                "<table width=\"100%%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"\n" +
                "       style=\"background-color:#f4f6f8; padding:40px 15px;\">\n" +
                "\n" +
                "    <tr>\n" +
                "        <td align=\"center\">\n" +
                "\n" +
                "            <table width=\"100%%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"\n" +
                "                   style=\"max-width:500px; background-color:#ffffff; border-radius:12px; overflow:hidden;\">\n" +
                "\n" +
                "                <!-- Header -->\n" +
                "                <tr>\n" +
                "                    <td align=\"center\"\n" +
                "                        style=\"background-color:#1029c8; padding:25px;\">\n" +
                "\n" +
                "                        <h1 style=\"margin:0; color:#ffffff; font-size:28px; font-weight:bold;\">\n" +
                "                            KUN.UZ\n" +
                "                        </h1>\n" +
                "\n" +
                "                        <p style=\"margin:8px 0 0; color:#ffffff; font-size:13px;\">\n" +
                "                            O'zbekiston yangiliklari\n" +
                "                        </p>\n" +
                "\n" +
                "                    </td>\n" +
                "                </tr>\n" +
                "\n" +
                "                <!-- Content -->\n" +
                "                <tr>\n" +
                "                    <td style=\"padding:35px 30px;\">\n" +
                "\n" +
                "                        <h2 style=\"margin:0 0 15px; color:#222222; font-size:22px;\">\n" +
                "                            Email manzilingizni tasdiqlang\n" +
                "                        </h2>\n" +
                "\n" +
                "                        <p style=\"margin:0 0 20px; color:#555555; font-size:15px; line-height:1.7;\">\n" +
                "                            Assalomu alaykum!\n" +
                "                            <br><br>\n" +
                "                            Kun.uz platformasida ro'yxatdan o'tganingiz uchun rahmat.\n" +
                "                            Email manzilingizni tasdiqlash uchun quyidagi kodni kiriting:\n" +
                "                        </p>\n" +
                "\n" +
                "                        <!-- Verification Code -->\n" +
                "                        <table width=\"100%%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n" +
                "                            <tr>\n" +
                "                                <td align=\"center\"\n" +
                "                                    style=\"background-color:#f4f6f8; border:1px dashed #1029c8;\n" +
                "                                           border-radius:10px; padding:22px;\">\n" +
                "\n" +
                "                                    <p style=\"margin:0 0 10px; color:#777777; font-size:12px;\">\n" +
                "                                        TASDIQLASH KODI\n" +
                "                                    </p>\n" +
                "\n" +
                "                                    <h1 style=\"margin:0; color:#1029c8;\n" +
                "                                               font-size:36px; letter-spacing:10px;\">\n" +
                "                                        %s\n" +
                "                                    </h1>\n" +
                "\n" +
                "                                </td>\n" +
                "                            </tr>\n" +
                "                        </table>\n" +
                "\n" +
                "                        <p style=\"margin:25px 0 10px; color:#555555; font-size:14px; line-height:1.6;\">\n" +
                "                            Ushbu kod <strong>5 daqiqa</strong> davomida amal qiladi.\n" +
                "                            Iltimos, kodni hech kimga bermang.\n" +
                "                        </p>\n" +
                "\n" +
                "                        <p style=\"margin:20px 0 0; color:#777777; font-size:13px; line-height:1.6;\">\n" +
                "                            Agar siz ushbu so'rovni amalga oshirmagan bo'lsangiz,\n" +
                "                            ushbu xabarni e'tiborsiz qoldirishingiz mumkin.\n" +
                "                        </p>\n" +
                "\n" +
                "                    </td>\n" +
                "                </tr>\n" +
                "\n" +
                "                <!-- Footer -->\n" +
                "                <tr>\n" +
                "                    <td align=\"center\"\n" +
                "                        style=\"background-color:#f4f6f8; padding:22px;\">\n" +
                "\n" +
                "                        <p style=\"margin:0 0 8px; color:#777777; font-size:12px;\">\n" +
                "                            &copy; 2026 Kun.uz. Barcha huquqlar himoyalangan.\n" +
                "                        </p>\n" +
                "\n" +
                "                        <p style=\"margin:0; color:#999999; font-size:11px;\">\n" +
                "                            Ushbu xabar avtomatik tarzda yuborildi.\n" +
                "                            Iltimos, javob bermang.\n" +
                "                        </p>\n" +
                "\n" +
                "                    </td>\n" +
                "                </tr>\n" +
                "\n" +
                "            </table>\n" +
                "\n" +
                "        </td>\n" +
                "    </tr>\n" +
                "\n" +
                "</table>\n" +
                "\n" +
                "</body>\n" +
                "</html>\n", code);

        try {
            MimeMessage msg = javaMailSender.createMimeMessage();
            msg.setFrom(fromAccount);

            MimeMessageHelper helper = new MimeMessageHelper(msg, true, StandardCharsets.UTF_8.name());
            helper.setTo(toAccount);
            helper.setSubject(title);
            helper.setText(text, true);
            javaMailSender.send(msg);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }

        return "Mail was send";
    }
//
//    public void sendMimeMessage(MessageDTO messageDTO) {
//    }
}
