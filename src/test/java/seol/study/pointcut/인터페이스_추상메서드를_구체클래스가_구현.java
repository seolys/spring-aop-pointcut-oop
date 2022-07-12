package seol.study.pointcut;

import static org.assertj.core.api.Assertions.*;

import java.lang.reflect.Method;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;

public class 인터페이스_추상메서드를_구체클래스가_구현 {

	AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();

	@Test
	void 인터페이스로_추상메서드_포인트컷__성공() throws NoSuchMethodException {
		pointcut.setExpression("execution(* seol.study.pointcut.Interface.*(..))"); // Pointcut: Interface

		final Method internalMethod = Interface.class.getMethod("interfaceMethod", String.class); // Method: Interface.interfaceMethod
		assertThat(pointcut.matches(internalMethod, InterfaceImpl.class)).isTrue();
	}

	@Test
	void 구체클래스로_추상메서드_포인트컷__성공() throws NoSuchMethodException {
		pointcut.setExpression("execution(* seol.study.pointcut.InterfaceImpl.*(..))"); // Pointcut: InterfaceImpl

		final Method internalMethod = InterfaceImpl.class.getMethod("interfaceMethod", String.class); // Method: Interface.interfaceMethod
		assertThat(pointcut.matches(internalMethod, InterfaceImpl.class)).isTrue();
	}

	/**
	 * 인터페이스가 구체클래스의 내부메서드 포인트컷 불가.
	 * - 인터페이스 입장에서 구체클래스의 내부메서드를 모를테니 당연함.
	 */
	@Test
	void 인터페이스로_구체_내부메서드_포인트컷__실패() {
		pointcut.setExpression("execution(* seol.study.pointcut.Interface.*(..))"); // Pointcut: Interface

		Assertions.assertThatThrownBy(() -> {
					Interface.class.getMethod("internalMethod", String.class); // Method: InterfaceImpl.internalMethod
				})
				.as("인터페이스는 구체클래스의 내부메서드를 찾을 수 없다.")
				.isInstanceOf(NoSuchMethodException.class);
	}

	@Test
	void 구체클래스로_구체_내부메서드_포인트컷__성공() throws NoSuchMethodException {
		pointcut.setExpression("execution(* seol.study.pointcut.InterfaceImpl.*(..))"); // Pointcut: InterfaceImpl

		final Method internalMethod = InterfaceImpl.class.getMethod("internalMethod", String.class); // Method: InterfaceImpl.internalMethod
		assertThat(pointcut.matches(internalMethod, InterfaceImpl.class)).isTrue();
	}

}
