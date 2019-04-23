package com.mertpolat.webservice;

import java.util.Random;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.mertpolat.pojo.DatabaseConnection;
import com.mertpolat.pojo.User;

@RestController
public class CreditCheckService {
	@RequestMapping(value = "/creditCheck", method = RequestMethod.POST)
	public ResponseEntity<String> creditCheck(@RequestBody User user) throws Exception {
		DatabaseConnection db = new DatabaseConnection();

		int creditScore = CalculateCreditSkore(user.getTc()); // getCreditScore(user.getTc()); kredi skor bilgisinin daha �nceden
															  // yaz�ld��� varsay�lan kredi skor servisten d�nd��� varsay�lm��t�r.														  
		if (creditScore < 500) {
			user.setLimit(0);
			user.setCreditStatus(false);
			db.AddUser(user);
			db.AddMessage(user, "Kredi ba�vurunuz red edilmi�tir");

			return new ResponseEntity<String>("Kredi ba�vurunuz red edilmi�tir.", HttpStatus.OK);
		} else if (creditScore >= 500 && creditScore < 1000) {
			if (user.getMonthlyIncome() < 5000) {
				user.setLimit(10000);
				user.setCreditStatus(true);
				db.AddUser(user);
				db.AddMessage(user, user.getLimit() + " Tl lik krediniz haz�r");
				
				return new ResponseEntity<String>(user.getLimit() + " Tl lik krediniz haz�r", HttpStatus.OK);
			}
		} else {
			user.setLimit(4 * user.getMonthlyIncome()); // 4 = kredi limit �arpan�
			user.setCreditStatus(true);
			db.AddUser(user);
			db.AddMessage(user, user.getLimit() + " Tl lik krediniz haz�r");

			return new ResponseEntity<String>(user.getLimit() + " Tl lik krediniz haz�r", HttpStatus.OK);
		}
		System.out.println(user);
		return null;
	}

	public int CalculateCreditSkore(String tc) {
		// Kredi Skoru servisinin oldu�u ve skoru hesaplad��� varsay�lm��t�r.
		// Bunun i�in random atama yaparak test caseleri olu�turulmu�tur.
		Random rast = new Random(); // random s�n�f�
		int creditSkore = rast.nextInt(2000);
		return creditSkore;
	}
}