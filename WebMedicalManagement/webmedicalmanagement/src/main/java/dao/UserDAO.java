package dao;

import java.util.List;

import model.User;

public interface UserDAO {
	int addUser(User user);
	int updateUser(User user);
	int deleteUser(int userId);
	User getUserById(int userId);
	User getUserByUsername(String username);
	List<User> getAllUsers();
	public List<User> getAllUsersByRole(String role);
}
