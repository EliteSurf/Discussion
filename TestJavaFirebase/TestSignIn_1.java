public void userLogin(String userEmail, String userPassword, String userRole) {
	// Verify using Firebase Auth
	firebaseAuth.signInWithEmailAndPassword(userName, userPassword)
    		.addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
            	@Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                	Log.i(TAG, "FirebaseAuth : Complete");                    
                    if (task.isSuccessful()) {
                    	// Success Pass Firebase Auth
                    	Log.i(TAG, "FirebaseAuth : Successful");
                    	// Go to userMenu method to determine role menu
                        userMenu(userEmail, userRole);                     
                    } else {
                        Log.i(TAG, "Login Failed", task.getException());
                        Toast.makeText(getActivity(), "Login Failed", Toast.LENGTH_SHORT).show();
                    }
                }
    });
}

public void userMenu(String userEmail, String userRole) {
	Log.i(TAG, "Determine User Menu Path");

	if (userRole.equals("Admin")) {
		// Firebase Key Value
		Query query = DatabaseReference.orderByChild("userEmail").equalTo(userEmail);
		query.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    Log.i(TAG, "DataSnapshot : onDataChange");
                    // Looping in Firebase Database
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    	// UserInfo Object Class
                        UserInfo userInfo = dataSnapshot1.getValue(UserInfo.class);
                        if(userInfo.getRole().equals(role)){
                            Log.i(TAG, "Admin Login : Success");
                            Toast.makeText(getActivity(), "Welcome " + userInfo.getName(), Toast.LENGTH_LONG).show();
                            // Go to another page
                            Intent intent = new Intent(getActivity(), BuyerMenuActivity.class);
                            // Forward the variable to next page
                            intent.putExtra("NAME", userInfo.getName());
                            startActivity(intent);
                            getActivity().finish();
                            return;
                        }
                    }
                    Log.i(TAG, "Wrong Role Selected");
                    Toast.makeText(getActivity(), "Incorrect Role Selected", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
	}
}