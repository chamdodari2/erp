package erp.ui.exception;

@SuppressWarnings("serial")
public class InvalidChechException extends RuntimeException { //전파. 계속 위롱 ㅗㄹ라간다

	public InvalidChechException() {
		super("공백이 존재합니다.");
	}

	public InvalidChechException(Throwable cause) {
		super("공백이 존재합니다.",cause);
		
	}
	

}
