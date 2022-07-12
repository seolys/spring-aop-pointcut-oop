package seol.study.pointcut;

import static org.assertj.core.api.Assertions.*;

import java.lang.reflect.Method;
import org.junit.jupiter.api.Test;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;

public class 인터페이스_추상메서드를_추상클래스가_구현한_상황 {

	AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();

	@Test
	void 인터페이스로_추상메서드_포인트컷() throws NoSuchMethodException {
		pointcut.setExpression("execution(* seol.study.pointcut.AbstractInterface.*(..))"); // 인터페이스

		final Method internalMethod = AbstractInterface.class.getMethod("abstractImplementMethod", String.class); // 인터페이스의 추상메서드
		assertThat(pointcut.matches(internalMethod, InterfaceImpl.class)).isTrue();
	}

	@Test
	void 추상클래스로_추상메서드_포인트컷() throws NoSuchMethodException {
		pointcut.setExpression("execution(* seol.study.pointcut.AbstractClass.*(..))"); // 추상클래스

		final Method internalMethod = AbstractClass.class.getMethod("abstractImplementMethod", String.class); // 인터페이스의 추상메서드 (override 함)
		assertThat(pointcut.matches(internalMethod, InterfaceImpl.class)).isTrue();
	}

	@Test
	void 구체클래스로_추상메서드_포인트컷() throws NoSuchMethodException {
		pointcut.setExpression("execution(* seol.study.pointcut.InterfaceImpl.*(..))"); // 구체클래스

		final Method internalMethod = InterfaceImpl.class.getMethod("abstractImplementMethod", String.class); // 인터페이스의 추상메서드 (override 하지않음)
		assertThat(pointcut.matches(internalMethod, InterfaceImpl.class))
				.as("구체 클래스는 추상클래스의 인터페이스 구현체 메서드를 포인트컷 할 수 없다.")
				.isFalse();
	}

	@Test
	void 추상클래스로_추상클래스의_내부메서드_포인트컷() throws NoSuchMethodException {
		pointcut.setExpression("execution(* seol.study.pointcut.AbstractClass.*(..))"); // 추상클래스

		final Method internalMethod = AbstractClass.class.getMethod("abstractInternalMethod", String.class); // 추상클래스의 내부메서드
		assertThat(pointcut.matches(internalMethod, InterfaceImpl.class)).isTrue();
	}

	@Test
	void 구체클래스로_추상클래스의_내부메서드_포인트컷() throws NoSuchMethodException {
		pointcut.setExpression("execution(* seol.study.pointcut.InterfaceImpl.*(..))"); // 구체클래스

		final Method internalMethod = InterfaceImpl.class.getMethod("abstractInternalMethod", String.class); // 추상클래스의 내부메서드
		assertThat(pointcut.matches(internalMethod, InterfaceImpl.class))
				.as("구체 클래스는 추상클래스의 내부메서드를 포인트컷 할 수 없다.")
				.isFalse();
	}

}
