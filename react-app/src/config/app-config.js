let backendHost;

const hostname = window && window.location && window.location.hostname;

// 로컬에서 개발할 경우
if ( hostname === "localhost") {
    backendHost = "http://localhost:8080";
} else {
    backendHost = "http://ec2-43-201-97-14.ap-northeast-2.compute.amazonaws.com:8080";
    // backendHost = "http://prod-todo-server-env.eba-jivgxvh2.ap-northeast-2.elasticbeanstalk.com:8080";
}

export const API_BASE_URL = backendHost;