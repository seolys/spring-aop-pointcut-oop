package seol.study.pointcut;

import static org.assertj.core.api.Assertions.*;

import java.lang.reflect.Method;
import org.junit.jupiter.api.Test;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;

public class 인터페이스_추상메서드를_추상클래스가_구현 {

	AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();

	@Test
	void 인터페이스로_추상메서드_포인트컷__성공() throws NoSuchMethodException {
		pointcut.setExpression("execution(* seol.study.pointcut.AbstractInterface.*(..))"); // Pointcut: AbstractInterface

		final Method internalMethod = AbstractInterface.class.getMethod("abstractImplementMethod", String.class); // Method: AbstractInterface.abstractImplementMethod
		assertThat(pointcut.matches(internalMethod, InterfaceImpl.class)).isTrue();
	}

	@Test
	void 추상클래스로_추상메서드_포인트컷__성공() throws NoSuchMethodException {
		pointcut.setExpression("execution(* seol.study.pointcut.AbstractClass.*(..))"); // Pointcut: AbstractClass

		final Method internalMethod = AbstractClass.class.getMethod("abstractImplementMethod", String.class); // Method: AbstractInterface.abstractImplementMethod
		assertThat(pointcut.matches(internalMethod, InterfaceImpl.class)).isTrue();
	}

	/**
	 * 자식클래스에서 부모클래스의 메서드 포인트컷 불가. (추상메서드)
	 */
	@Test
	void 구체클래스로_추상메서드_포인트컷__실패() throws NoSuchMethodException {
		pointcut.setExpression("execution(* seol.study.pointcut.InterfaceImpl.*(..))"); // Pointcut: AbstractClass

		final Method internalMethod = InterfaceImpl.class.getMethod("abstractImplementMethod", String.class); // Method: AbstractInterface.abstractImplementMethod
		assertThat(pointcut.matches(internalMethod, InterfaceImpl.class))
				.as("구체 클래스는 추상클래스의 인터페이스 구현체 메서드를 포인트컷 할 수 없다.")
				.isFalse();
	}

	@Test
	void 추상클래스로_추상클래스의_내부메서드_포인트컷__성공() throws NoSuchMethodException {
		pointcut.setExpression("execution(* seol.study.pointcut.AbstractClass.*(..))"); // Pointcut: AbstractClass

		final Method internalMethod = AbstractClass.class.getMethod("abstractInternalMethod", String.class); // Method: AbstractClass.abstractInternalMethod
		assertThat(pointcut.matches(internalMethod, InterfaceImpl.class)).isTrue();
	}

	/**
	 * 자식클래스에서 부모클래스의 메서드 포인트컷 불가. (부모 내부메서드)
	 */
	@Test
	void 구체클래스로_추상클래스의_내부메서드_포인트컷__실패() throws NoSuchMethodException {
		pointcut.setExpression("execution(* seol.study.pointcut.InterfaceImpl.*(..))"); // Pointcut: InterfaceImpl

		final Method internalMethod = InterfaceImpl.class.getMethod("abstractInternalMethod", String.class); // Method: AbstractClass.abstractInternalMethod
		assertThat(pointcut.matches(internalMethod, InterfaceImpl.class))
				.as("구체 클래스는 추상클래스의 내부메서드를 포인트컷 할 수 없다.")
				.isFalse();
	}

}
