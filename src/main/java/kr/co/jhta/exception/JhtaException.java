package kr.co.jhta.exception;

public class JhtaException extends RuntimeException {
	
//	네트워크를 통해서 객체를 직렬화할 때 객체를 식별할 때 사용하는 시리얼 번호
	private static final long serialVersionUID = 6358721302834790336L;

	public JhtaException(String message) {
		super(message);
	}
	
	public JhtaException(String message, Throwable cause) {
		super(message, cause);
	}

}
