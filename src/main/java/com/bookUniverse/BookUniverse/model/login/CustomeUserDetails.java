//package com.bookUniverse.BookUniverse.model.login;
//
//@Data
//@NoArgsConstructor
//public class CustomeUserDetails implements UserDetails {
//    public String email;
//    public String password;
//    public List<GrantedAuthority> grantedAuthorities;
//
//    public CustomeUserDetails(User user){
//        this.email = user.getEmail();
//        this.password=user.getPassword();
//        this.grantedAuthorities = List.of(new SimpleGrantedAuthority(user.getRoles().name()));
//    }
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return grantedAuthorities;
//    }
//
//    @Override
//    public String getPassword() {
//        return password;
//    }
//
//    @Override
//    public String getUsername() {
//        return email;
//    }
//
//    @Override
//    public boolean isAccountNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isAccountNonLocked() {
//        return true;
//    }
//
//    @Override
//    public boolean isCredentialsNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isEnabled() {
//        return true;
//    }
//}