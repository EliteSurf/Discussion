public void registerUser(String userEmail, String userPassword, String userName, String userPhone, String userRole){
	// Detect Dulpicate userName (Firebase Database)
	DatabaseReference = FirebaseDatabase.getInstance().getReference("Users").child(userName);
	DatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
		@Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            if(dataSnapshot.exists()){
            	Log.i(TAG, "Username : " + userName + " Had Already Exist");
                Toast.makeText(getActivity(), "Username Exist !", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
        	return;
        }
    });

	// Register with Firebase Auth
	firebaseAuth.createUserWithEmailAndPassword(userEmail, userPassword)
    		.addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
            	@Override
            	public void onComplete(@NonNull Task<AuthResult> task) {
            		if (!task.isSuccessful()) {
                    	Log.i(TAG, "FirebaseAuth Register : Fail");
                        Toast.makeText(getActivity(), "Could Not Register Your Account", Toast.LENGTH_SHORT).show();
                        return;
                    } else {
                    	Log.i(TAG, "Buyer FirebaseAuth Register : Success");
                    	// Input all data into Firebase Database
                    	DatabaseReference = FirebaseDatabase.getInstance().getReference("Users").child(userName);
                    	final UserInfo userInfo = new UserInfo(userEmail, userName, userPhone, userRole) 
                    	DatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    		@Override
                    		public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            	if(!dataSnapshot.exists()) {
                                DatabaseReference.setValue(userInfo);          
                                Log.i(TAG, "FirebaseDatabase : Success");
                                Toast.makeText(getActivity(), "Register Complete", Toast.LENGTH_SHORT).show();
                                }
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                            	Log.w(TAG, "Database Error");
                            }


                    	});
                    }

            	}
    }
}