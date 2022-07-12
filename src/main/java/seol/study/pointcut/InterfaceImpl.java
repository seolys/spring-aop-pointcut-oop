package seol.study.pointcut;


public class InterfaceImpl extends AbstractClass implements Interface {

	@Override
	public String interfaceMethod(final String param) {
		return "ok";
	}

	public String internalMethod(String param) {
		return "ok";
	}

}
