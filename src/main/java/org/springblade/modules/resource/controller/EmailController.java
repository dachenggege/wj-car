package org.springblade.modules.resource.controller;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/mail")
@Api(value = "邮件", tags = "邮件")
public class EmailController {

	@Autowired
	private JavaMailSender mailSender;

	@GetMapping("/send")
	@ApiOperation(value = "发送邮件")
	public void sendMail(){
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom("songyc@szmlink.com");
		message.setTo("xiongwc@szmlink.com");
		message.setSubject("脉联电子奥里给");
		message.setText("你好，我是大成哥哥，我正在测试发送邮件。");

			mailSender.send(message);


	}
}
