package telran.net;

import telran.net.dto.*;

public class CalculatorProtocol implements ApplProtocolJava {

	@Override
	public ResponseJava getResponse(RequestJava request) {
		try {
			switch (request.requestType) {
				case "calculate" : 
					return new ResponseJava(
						ResponseCode.OK, 
						(String) request.data);
				default: 
					return new ResponseJava(
						ResponseCode.WRONG_REQUEST_TYPE, 
						new IllegalArgumentException(request.requestType + " not exists."));
			}
		} catch (Exception exception) {
			return new ResponseJava(ResponseCode.WRONG_REQUEST_DATA, exception);
		}
		
	}

}
