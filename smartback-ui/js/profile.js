async function loadProfile() {

    checkAuthentication();

    try {

        // Change endpoint according to your Account Service
        const response = await get("/account/profile");

        if (!response.ok) {
            alert("Unable to load profile.");
            return;
        }

        const profile = await response.json();

        document.getElementById("customerId").innerText =
            profile.customerId ?? "-";

        document.getElementById("name").innerText =
            profile.name ?? "-";

        document.getElementById("username").innerText =
            profile.username ?? "-";

        document.getElementById("email").innerText =
            profile.email ?? "-";

        document.getElementById("phone").innerText =
            profile.phone ?? "-";

        document.getElementById("accountNumber").innerText =
            profile.accountNumber ?? "-";

        document.getElementById("accountType").innerText =
            profile.accountType ?? "-";

        document.getElementById("balance").innerText =
            "₹ " + (profile.balance ?? "0.00");

        document.getElementById("status").innerText =
            profile.status ?? "ACTIVE";

    } catch (e) {

        console.error(e);
        alert("Server unavailable.");

    }

}