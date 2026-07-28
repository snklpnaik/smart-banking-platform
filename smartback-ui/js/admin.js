async function loadAdminDashboard() {

    checkAuthentication();

    if (!isAdmin()) {
        alert("Access Denied.");
        window.location.href = "dashboard.html";
        return;
    }

    viewAllAccounts();

}

async function createAccount() {

    alert("Create Account functionality can be implemented using your Create Account API.");

}

async function updateAccount() {

    const accountNumber = document.getElementById("accountNumber").value.trim();

    if (accountNumber === "") {
        alert("Enter Account Number.");
        return;
    }

    alert("Redirect to Update Account page or open update form.");

}

async function deleteAccount() {

    const accountNumber = document.getElementById("accountNumber").value.trim();

    if (accountNumber === "") {
        alert("Enter Account Number.");
        return;
    }

    if (!confirm("Are you sure you want to delete this account?")) {
        return;
    }

    try {

        const response = await remove("/account/" + accountNumber);

        if (response.ok) {

            alert("Account deleted successfully.");

            viewAllAccounts();

        } else {

            alert("Unable to delete account.");

        }

    } catch (e) {

        console.error(e);

        alert("Server unavailable.");

    }

}

async function searchAccount() {

    const accountNumber = document.getElementById("accountNumber").value.trim();

    if (accountNumber === "") {
        alert("Enter Account Number.");
        return;
    }

    try {

        const response = await get("/account/" + accountNumber);

        if (!response.ok) {

            alert("Account not found.");

            return;

        }

        const account = await response.json();

        document.getElementById("accNumber").innerText =
            account.accountNumber ?? "-";

        document.getElementById("customerName").innerText =
            account.name ?? "-";

        document.getElementById("email").innerText =
            account.email ?? "-";

        document.getElementById("phone").innerText =
            account.phone ?? "-";

        document.getElementById("accountType").innerText =
            account.accountType ?? "-";

        document.getElementById("balance").innerText =
            "₹ " + (account.balance ?? "0.00");

        document.getElementById("status").innerText =
            account.status ?? "-";

    } catch (e) {

        console.error(e);

        alert("Server unavailable.");

    }

}

async function viewAllAccounts() {

    const table = document.getElementById("accountTable");

    table.innerHTML = "";

    try {

        const response = await get("/account/all");

        if (!response.ok) {

            table.innerHTML =
                "<tr><td colspan='5'>Unable to load accounts.</td></tr>";

            return;

        }

        const accounts = await response.json();

        if (accounts.length === 0) {

            table.innerHTML =
                "<tr><td colspan='5'>No accounts found.</td></tr>";

            return;

        }

        accounts.forEach(account => {

            table.innerHTML += `
                <tr>
                    <td>${account.accountNumber}</td>
                    <td>${account.name}</td>
                    <td>${account.accountType}</td>
                    <td>₹ ${account.balance}</td>
                    <td>${account.status}</td>
                </tr>
            `;

        });

    } catch (e) {

        console.error(e);

        table.innerHTML =
            "<tr><td colspan='5'>Server unavailable.</td></tr>";

    }

}