const BASE_URL = "http://localhost:8086";

const API = {

    // Auth Service
    LOGIN: "/auth/login",
    REGISTER: "/auth/register",
    PROFILE: "/auth/profile",

    // Account Service
    CREATE_ACCOUNT: "/accounts",
    GET_ACCOUNT: "/account",
    GET_USER_ACCOUNTS: "/accounts/user",
    CREDIT: "/accounts/credit",
    DEBIT: "/accounts/debit",
    UPDATE_BALANCE: "/accounts/balance",

    // Transaction Service
    DEPOSIT: "/transactions/deposit",
    WITHDRAW: "/transactions/withdraw",
    TRANSFER: "/transactions/transfer",
    TRANSACTIONS: "/transactions"
};

function getToken() {
    return localStorage.getItem("token");
}

function getHeaders() {

    const headers = {
        "Content-Type": "application/json"
    };

    const token = getToken();

    if (token) {
        headers["Authorization"] = "Bearer " + token;
    }

    return headers;
}

async function get(endpoint) {

    return await fetch(BASE_URL + endpoint, {
        method: "GET",
        headers: getHeaders()
    });

}

async function post(endpoint, body) {

    return await fetch(BASE_URL + endpoint, {
        method: "POST",
        headers: getHeaders(),
        body: JSON.stringify(body)
    });

}

async function put(endpoint, body) {

    return await fetch(BASE_URL + endpoint, {
        method: "PUT",
        headers: getHeaders(),
        body: JSON.stringify(body)
    });

}

async function remove(endpoint) {

    return await fetch(BASE_URL + endpoint, {
        method: "DELETE",
        headers: getHeaders()
    });

}