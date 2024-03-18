package in.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.AuthenticationFilter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyController {
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private MyUserDetailsService myUserDetailsService;
	
	@Autowired
	JwtUtil jwtUtil;
	
	@GetMapping("/msg")
	public String getMsg() {
		return "Message";
	}
	
	
	@PostMapping("/authenticate")
	public LoginResponse getResponse(@RequestBody LoginRequest loginRequest)throws Exception {
		try {
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
		}
		catch(BadCredentialsException e) {
			throw new Exception("Incorrect User",e);
		}
		 UserDetails UserDetails =myUserDetailsService.loadUserByUsername(loginRequest.getUsername());
		
		 String jwtToken = jwtUtil.generateToken(UserDetails);
		 
		 return new LoginResponse(jwtToken);
	}

}
