package com.example.nagoyameshi.security;

 import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.nagoyameshi.entity.User;
 
 public class UserDetailsImpl implements UserDetails {
     private final User user;
//     private final String fullname;
//     private String fullname;
     private final Collection<GrantedAuthority> authorities;
//     private final Collection<GrantedAuthority> @Override
     
//     public UserDetailsImpl(User user, Collection<GrantedAuthority> authorities) {
//         this.user = user;
//         this.authorities = authorities;

     
     public UserDetailsImpl(User user, Collection<GrantedAuthority> authorities) {
    	 this.user = user;
//		 this.fullname = "";
    	 this.authorities = authorities;
     }
     
//     public User getUser() {
//         return user;
//   
     
     public User getUser() {
         return user;
     }
     
     public String getFullName() {
         return user.getFullName();
     }
     
     // ハッシュ化済みのパスワードを返す
     @Override
     public String getPassword() {
         return user.getPassword();
     }
     
     // ログイン時に利用するユーザー名（メールアドレス）を返す
     @Override
     public String getUsername() {
         return user.getEmail();
     }
     
//     public boolean isAdmin() {
//    	 return user.isAdmin();
//     }
     
//     public boolean isPaid() {
//    	 return user.isPaidLicense();
//     }
     
     // アカウントが期限切れでなければtrueを返す
     @Override
     public boolean isAccountNonExpired() {
         return true;
     }
     
     // ユーザーがロックされていなければtrueを返す
     @Override
     public boolean isAccountNonLocked() {
         return true;
     }    
     
     // ユーザーのパスワードが期限切れでなければtrueを返す
     @Override
     public boolean isCredentialsNonExpired() {
         return true;
     }
     
     @Override 
     public boolean isEnabled() {
    	 return true;
     }

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}
}