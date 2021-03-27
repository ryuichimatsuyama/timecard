package validators;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import models.Employee;
import utils.DBUtil;

public class EmployeeValidator {
	public static List<String> validate(Employee e, Boolean codeDuplicateCheckFlag, Boolean passwordCheckFlag) {
		List<String> errors = new ArrayList<String>();

		String code_error = validateCode(e.getCode(), codeDuplicateCheckFlag);
		if (!code_error.equals("")) {
			errors.add(code_error);
		}

		String name_error = validateName(e.getName());
		if (!name_error.equals("")) {
			errors.add(name_error);
		}

		String password_error = validatePassword(e.getPassword(), passwordCheckFlag);
		if (!password_error.equals("")) {
			errors.add(password_error);
		}

		String break_time_error = validateBreak_time(e.getBreak_time());
		if (!break_time_error.equals("")) {
			errors.add(break_time_error);
		}

		String wage_error = validateWage(e.getWage());
		if (!wage_error.equals("")) {
			errors.add(wage_error);
		}

		return errors;
	}

	// 社員番号
	private static String validateCode(String code, Boolean codeDuplicateCheckFlag) {
		// 必須入力チェック
		if (code == null || code.equals("")) {
			return "社員番号を入力してください。";
		}

		// すでに登録されている社員番号との重複チェック
		if (codeDuplicateCheckFlag) {
			EntityManager em = DBUtil.createEntityManager();
			long employees_count = (long) em.createNamedQuery("checkRegisteredCode", Long.class)
					.setParameter("code", code).getSingleResult();
			em.close();
			if (employees_count > 0) {
				return "入力された社員番号の情報はすでに存在しています。";
			}
		}

		return "";
	}

	// 社員名の必須入力チェック
	private static String validateName(String name) {
		if (name == null || name.equals("")) {
			return "氏名を入力してください。";
		}

		return "";
	}

	// パスワードの必須入力チェック
	private static String validatePassword(String password, Boolean passwordCheckFlag) {
		// パスワードを変更する場合のみ実行
		if (passwordCheckFlag && (password == null || password.equals(""))) {
			return "パスワードを入力してください。";
		}
		return "";
	}

	// 正規表現を使って時間らしい表示をしてるかチェック
	private static String validateBreak_time(String break_time) {
		if (!break_time.matches("^([01][0-9]|2[0-3]):[0-5][0-9]$")) {
			return "休憩時間を入力してください。";
		}

		return "";
	}

	// 正規表現を使って３か４桁の数字かチェック
	private static String validateWage(String wage) {
		if (!wage.matches("^[0-9]{3,4}$")) {
			return "時給を入力してください。";
		}

		return "";
	}
}
