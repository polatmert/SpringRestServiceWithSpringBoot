package com.mertpolat.webservice;

import java.util.Random;

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
	public ResponseEntity<String> creditCheck(@RequestBody User user) {
		DatabaseConnection db = new DatabaseConnection();

		int creditScore = CalculateCreditSkore(user.getTc()); // getCreditScore(user.getTc()); kredi skor bilgisinin alýndýðý varsayýlýyor
		if (creditScore < 500)
		{
			user.setLimit(0);
			user.setCreditStatus(false);
			db.Add(user);

			return new ResponseEntity<String>("Kredi baþvurunuz red edilmiþtir.", HttpStatus.OK);
		} else if (creditScore >= 500 && creditScore < 1000) 
		{
			if (user.getMonthlyIncome() < 5000) 
			{
				user.setLimit(10000);
				user.setCreditStatus(true);

			    //statement.executeUpdate("insert into tblRESIM(resimID,resimYol,resimEvId) VALUES  (1,'"+aa+"11.jpg',1)");
				db.Add(user);

				return new ResponseEntity<String>(user.getLimit() + " Tl lik krediniz hazýr", HttpStatus.OK);
			} 
		}
		else 
		{
			user.setLimit( 4 * user.getMonthlyIncome()); 
			user.setCreditStatus(true);
			db.Add(user);

			return new ResponseEntity<String>(user.getLimit() + " Tl lik krediniz hazýr", HttpStatus.OK);
		}
		System.out.println(user);
		// return new ResponseEntity<User>(user, HttpStatus.OK);
		return null;
	}

	public int CalculateCreditSkore(String tc) {
		// Kredi Skoru servisinin olduðu ve skoru hesapladýðý varsayýlmýþtýr.
		// Bunun için random atama yaparak test caseleri oluþturulmuþtur.
		Random rast = new Random(); // random sýnýfý
		int creditSkore = rast.nextInt(2000);
		return creditSkore;
	}

}
