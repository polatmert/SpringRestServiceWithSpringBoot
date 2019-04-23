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

		int creditScore = CalculateCreditSkore(user.getTc()); // getCreditScore(user.getTc()); kredi skor bilgisinin al�nd��� varsay�l�yor
		if (creditScore < 500)
		{
			user.setLimit(0);
			user.setCreditStatus(false);
			db.Add(user);

			return new ResponseEntity<String>("Kredi ba�vurunuz red edilmi�tir.", HttpStatus.OK);
		} else if (creditScore >= 500 && creditScore < 1000) 
		{
			if (user.getMonthlyIncome() < 5000) 
			{
				user.setLimit(10000);
				user.setCreditStatus(true);

			    //statement.executeUpdate("insert into tblRESIM(resimID,resimYol,resimEvId) VALUES  (1,'"+aa+"11.jpg',1)");
				db.Add(user);

				return new ResponseEntity<String>(user.getLimit() + " Tl lik krediniz haz�r", HttpStatus.OK);
			} 
		}
		else 
		{
			user.setLimit( 4 * user.getMonthlyIncome()); 
			user.setCreditStatus(true);
			db.Add(user);

			return new ResponseEntity<String>(user.getLimit() + " Tl lik krediniz haz�r", HttpStatus.OK);
		}
		System.out.println(user);
		// return new ResponseEntity<User>(user, HttpStatus.OK);
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
