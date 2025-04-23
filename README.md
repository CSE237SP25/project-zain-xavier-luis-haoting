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

# Iteration 2
What user stories were completed this iteration?
* Bank customer can create new account
* Bank Customer can login to existing account
* Bank customer can review only their transaction history
* Admins are able to login to a special admin account
  
What user stories do you intend to complete next iteration?
* Bank customers are able to transfer money between accounts
* Bank customers are able to specify a savings/checking account when creating new account

Is there anything that you implemented but doesn't currently work?
* Admin accounts have to be created within the codebase before you can access them in the menu, because the admin account creation is different from a regular account

# Iteration 3
What user stories were completed this iteration?
* Bank customer is intantly logged in to registered account
* Bank customer can review failed withdrawals in a new failed transaction log
* Admins can log in from the menu and access all admin features
* Bank customer can open several accounts and can differentiate between savings and checking account on creation

Is there anything that you implemented but doesn't currently work?
* Bank customer can transfer funds to another account --> needs to be put into user interface
* The savings account has internal functionality, but the UI doesn't show any changes and the monthly cycle to accrue interest and reset maximum withdrawals doesn't actually run

# Running Script
What commands are needed to compile and run your code from the command line (please provide a script that users can run to launch your program)?

* Navigate to the root of the repo, then run: 

* chmod +x runBankApp.sh

* ./runBankApp.sh
