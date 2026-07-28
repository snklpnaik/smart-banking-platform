async function loadDashboard() {

    checkAuthentication();

    try {

        // Get user details
        await loadProfile();

        document.getElementById("welcomeText").innerHTML =
            "Welcome, " + sessionStorage.getItem("userName");

        document.getElementById("email").innerHTML =
            sessionStorage.getItem("email");

        // Show Admin button if user is ADMIN
        if (isAdmin()) {
            document.getElementById("adminButton").style.display = "inline-block";
        }

        // Get logged in user's ID from JWT
        const userId = getUserId();

        // Get user's account(s)
        const response = await get(API.GET_USER_ACCOUNTS + "/" + userId);

        if (!response.ok) {
            alert("Unable to load account details.");
            return;
        }

        const accounts = await response.json();

        if (accounts.length === 0) {
            alert("No account found.");
            return;
        }

        // Taking first account
        const account = accounts[0];

        // Save for other pages
        sessionStorage.setItem("accountNumber", account.accountNumber);
        sessionStorage.setItem("accountType", account.accountType);
        sessionStorage.setItem("balance", account.balance);
        sessionStorage.setItem("status", account.status);

        // Display
        document.getElementById("accountNumber").innerHTML =
            account.accountNumber;

        document.getElementById("accountType").innerHTML =
            account.accountType;

        document.getElementById("balance").innerHTML =
            "₹ " + account.balance;

        document.getElementById("status").innerHTML =
            account.status;

    }
    catch (e) {

        console.error(e);

        alert("Unable to connect to server.");

    }

}