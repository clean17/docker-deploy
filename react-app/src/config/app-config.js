let backendHost;

const hostname = window && window.location && window.location.hostname;

// 로컬에서 개발할 경우
if ( hostname === "localhost") {
    backendHost = "http://localhost:8080";
} else {
    backendHost = "ec2-43-201-97-14.ap-northeast-2.compute.amazonaws.com";
}

export const API_BASE_URL = backendHost;