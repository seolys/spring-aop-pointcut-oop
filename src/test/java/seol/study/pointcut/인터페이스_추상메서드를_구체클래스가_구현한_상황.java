package seol.study.pointcut;

import static org.assertj.core.api.Assertions.*;

import java.lang.reflect.Method;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;

public class 인터페이스_추상메서드를_구체클래스가_구현한_상황 {

	AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();

	@Test
	void 인터페이스로_추상메서드_포인트컷() throws NoSuchMethodException {
		pointcut.setExpression("execution(* seol.study.pointcut.Interface.*(..))"); // 인터페이스

		final Method internalMethod = Interface.class.getMethod("interfaceMethod", String.class); // 인터페이스의 추상메서드
		assertThat(pointcut.matches(internalMethod, InterfaceImpl.class)).isTrue();
	}

	@Test
	void 구체클래스로_추상메서드_포인트컷() throws NoSuchMethodException {
		pointcut.setExpression("execution(* seol.study.pointcut.InterfaceImpl.*(..))"); // 구체클래스

		final Method internalMethod = InterfaceImpl.class.getMethod("interfaceMethod", String.class); // 인터페이스의 추상메서드
		assertThat(pointcut.matches(internalMethod, InterfaceImpl.class)).isTrue();
	}

	@Test
	void 인터페이스로_구체_내부메서드_포인트컷() {
		pointcut.setExpression("execution(* seol.study.pointcut.Interface.*(..))"); // 인터페이스

		Assertions.assertThatThrownBy(() -> {
					Interface.class.getMethod("internalMethod", String.class); // 구체클래스 메서드
				})
				.as("인터페이스는 구체클래스의 내부메서드를 찾을 수 없다.")
				.isInstanceOf(NoSuchMethodException.class);
	}

	@Test
	void 구체클래스로_구체_내부메서드_포인트컷() throws NoSuchMethodException {
		pointcut.setExpression("execution(* seol.study.pointcut.InterfaceImpl.*(..))"); // 구체클래스

		final Method internalMethod = InterfaceImpl.class.getMethod("internalMethod", String.class); // 구체클래스 메서드
		assertThat(pointcut.matches(internalMethod, InterfaceImpl.class)).isTrue();
	}
}
