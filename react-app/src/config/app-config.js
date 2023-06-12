let backendHost;

const hostname = window && window.location && window.location.hostname;

// 로컬에서 개발할 경우
if ( hostname === "localhost") {
    backendHost = "http://localhost:8080";
}

export const API_BASE_URL = backendHost;