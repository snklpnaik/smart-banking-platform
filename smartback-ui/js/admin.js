async function loadAdminDashboard() {

    checkAuthentication();

    if (!isAdmin()) {

        alert("Access Denied");

        window.location.href = "dashboard.html";

        return;

    }

}

async function searchAccount() {

    const accountNumber =
        document.getElementById("accountNumber").value.trim();

    if (accountNumber === "") {

        alert("Enter Account Number.");

        return;

    }

    try {

        const response =
            await get(API.GET_ACCOUNT + "/" + accountNumber);

        if (!response.ok) {

            alert("Account not found.");

            return;

        }

        const account = await response.json();

        document.getElementById("accNumber").innerHTML =
            account.accountNumber;

        document.getElementById("userId").innerHTML =
            account.userId;

        document.getElementById("accountType").innerHTML =
            account.accountType;

        document.getElementById("balance").innerHTML =
            "₹ " + account.balance;

        document.getElementById("status").innerHTML =
            account.status;

        document.getElementById("createdAt").innerHTML =
            account.createdAt;

    }
    catch (e) {

        console.error(e);

        alert("Unable to connect to server.");

    }

}

async function viewAllAccounts() {

    alert("Your backend doesn't expose GET /account/all yet.");

}

async function createAccount() {

    alert("Connect this button with POST /account.");

}

async function updateBalance() {

    alert("Connect this button with PUT /account/balance.");

}

async function creditAccount() {

    alert("Connect this button with PUT /account/credit.");

}

async function debitAccount() {

    alert("Connect this button with PUT /account/debit.");

}