package com.niit.onlinecollaboration.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.niit.onlinecollaboration.dao.UserDao;
import com.niit.onlinecollaboration.model.DomainResponse;
import com.niit.onlinecollaboration.model.User_Detail;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserDao userDao;
	@Autowired
	private HttpSession session;
	@RequestMapping("/get/{id}")
	public ResponseEntity<User_Detail> getUserById(@PathVariable int id) {
		System.out.println("-------------------------------------reached into controller--------------------------------------------");
		return new ResponseEntity<User_Detail>(userDao.getUserDetail(id), HttpStatus.OK);
	}

	@PostMapping("/insert")
	public ResponseEntity<DomainResponse> post(@RequestBody User_Detail user){
		System.out.println("-------------------------------------reached into controller1--------------------------------------------");
		userDao.add(user);
		System.out.println("-------------------------------user adeed successfully--------------");
		return new ResponseEntity<DomainResponse> (new DomainResponse("user recieved the data",100), HttpStatus.OK);
	}
	
	@RequestMapping("/all")
	public ResponseEntity<List<User_Detail>> getList(){
		return new ResponseEntity<List<User_Detail>>(userDao.userlist(),HttpStatus.OK);
	}
	
	@GetMapping("/myprofile")
	public ResponseEntity<DomainResponse> myProfile() {
		
		int loggedInUserID = (int) session.getAttribute("loggedInUserID");
		User_Detail user = userDao.getUserDetail(loggedInUserID);
		if (user == null) {
			
			user = new User_Detail(); 
	
//			return new ResponseEntity<DomainResonse>(new DomainResponse("Profile is not found",400), HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<DomainResponse>(new DomainResponse("My Profile",100), HttpStatus.OK);
	}
	
	
	@RequestMapping("/delete/{id}")
	public ResponseEntity<DomainResponse> deleteUserById(@PathVariable int id) {
		userDao.delete(id); 
		return new ResponseEntity<DomainResponse>(new DomainResponse("deleted the data",100), HttpStatus.OK);

	}
	
	@PostMapping("/update/{id}")
	public ResponseEntity<DomainResponse> updateUser(@RequestBody User_Detail user,@PathVariable int id){
		
		User_Detail currentUser = userDao.getUserDetail(id);
		currentUser.setUserId(user.getUserId());
		currentUser.setAddress(user.getAddress());
		currentUser.setActive(user.isActive());
		currentUser.setCity(user.getCity());
		currentUser.setEmail(user.getEmail());
		currentUser.setGender(user.getGender());
		currentUser.setIsOnline(user.getIsOnline());
		currentUser.setName(user.getName());
		currentUser.setPassword(user.getPassword());
		currentUser.setPhoneNo(user.getPhoneNo());
		currentUser.setProfile(user.getProfile());
		currentUser.setRole(user.getRole());
		currentUser.setState(user.getState());
		currentUser.setUserName(user.getUserName());
		currentUser.setStatus(user.getStatus());
		currentUser.setBirthDate(user.getBirthDate());
		userDao.update(currentUser);
		
		return new ResponseEntity<DomainResponse> (new DomainResponse("user recieved the data",100), HttpStatus.OK);
	}
	
	
	@PostMapping("/login")
public ResponseEntity<User_Detail> login(@RequestBody User_Detail user) {
	
		System.out.println("Welcomeeeeeeeeeeeeeeeeeeeeeeeeeeaweaaaaaaaaaaaaaaaaaaaaaaaaabfgd");
	user = userDao.authenticate(user.getUserId(), user.getPassword());
//	if (user == null) {
//		System.out.println("+++++++++++Welcome++++++++++++++++++++++++++++++");
//		user = new User_Detail(); 
//		System.out.println("******************************Welcome*****************************************");
//	
//		return new ResponseEntity<DomainResponse>(new DomainResponse("You can not login.Please enter valid Userid and password",400), HttpStatus.OK);
//
//	} else
//
//	{
		user.setIsOnline("yes");
		session.setAttribute("loggedInUserID", user.getUserId());
		session.setAttribute("loggedInUserRole", user.getRole());
		return new ResponseEntity<User_Detail>(HttpStatus.OK);
//	}
}
	
//	@RequestMapping(value = "/logout", method = RequestMethod.GET)
//	public ResponseEntity<DomainResponse> logout(HttpSession session) {
//		int loggedInUserID = (Integer) session.getAttribute("loggedInUserID");
//		
//		 User_Detail user = userDao.get(loggedInUserID);
//		
//		 
//
//		 userDao.update(user);
//		 
//		
////		friendDAO.setOffLine(loggedInUserID);
//		userDao.setOffLine(loggedInUserID);
//
//		session.invalidate();
//
//	
//		return new ResponseEntity<DomainResponse>(new DomainResponse("You have successfully logout",100), HttpStatus.OK);
//	};
}
