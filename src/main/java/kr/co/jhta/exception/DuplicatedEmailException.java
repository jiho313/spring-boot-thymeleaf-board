package kr.co.jhta.exception;

public class DuplicatedEmailException extends JhtaException {
	
//	네트워크를 통해서 객체를 직렬화할 때 객체를 식별할 때 사용하는 시리얼 번호
	private static final long serialVersionUID = -391884139794026693L;

	public DuplicatedEmailException(String message) {
		super(message);
	}

}
