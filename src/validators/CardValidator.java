package validators;

import java.util.ArrayList;
import java.util.List;

import models.Card;

public class CardValidator {
	public static List<String> validate(Card e) {
		List<String> errors = new ArrayList<String>();

		String start_error = validateStart(e.getStart());
		if (!start_error.equals("")) {
			errors.add(start_error);
		}

		String end_error = validateEnd(e.getEnd());
		if (!end_error.equals("")) {
			errors.add(end_error);
		}

		return errors;
	}

	// 正規表現を使って時間らしい表示をしてるかチェック
	private static String validateStart(String start) {
		if (!start.matches("^([01][0-9]|2[0-3]):[0-5][0-9]$")) {
			return "出勤時間を入力してください。";
		}

		return "";
	}

	private static String validateEnd(String end) {
		if (!end.matches("^([01][0-9]|2[0-3]):[0-5][0-9]$")) {
			return "退勤時間を入力してください。";
		}

		return "";
	}
}
