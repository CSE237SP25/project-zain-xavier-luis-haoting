# cse237-project25

Team Members:

* Xavier Thomas-Lewis

* Luis Robles

# Iteration 1
What user stories were completed this iteration?
* Bank customer can deposit into account
* Bank customer can withdraw from account
* Bank customer can check account balance
* Bank customer can view past transactions
  
What user stories do you intend to complete next iteration?
* Users should be forced to log in to an account before accessing other options in menu
* Admins should be able to login to a special admin account
* Transactions should have an ID assigned according to who made the transaction
* Admins should be able to access all accounts and transactions
* Customers should be able to review only their own transactions
* Bank customers should be able to transfer money to a different account

Is there anything that you implemented but doesn't currently work?
* Code exists for creating new accounts and logging into existing accounts, but this is not yet included in the menu
* Menu currently has a sanitization issue; any non-numerical inputs result in an input mismatch error

What commands are needed to compile and run your code from the command line (please provide a script that users can run to launch your program)?
* javac bankapp/*.java
* java bankapp.Menu
