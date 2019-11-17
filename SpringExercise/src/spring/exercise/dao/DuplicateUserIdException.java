package spring.exercise.dao;

public class DuplicateUserIdException extends RuntimeException {

	public DuplicateUserIdException(Throwable cause) {
		super(cause);
	}
}
