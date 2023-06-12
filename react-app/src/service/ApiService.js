import { API_BASE_URL } from '../app-config';

let errorMsg;

function createOptions(api, method, request) {
    let options = {
        headers: new Headers({
            "content-type": "application/json; charset=utf-8",
        }),
        url: API_BASE_URL + api,
        method: method,
    };

    // false, null, undefined, 0, NaN, '' 를 제외한 오브젝트는 truthy 로 간주 / 반대는 falsy 
    if (request) {
        options.body = JSON.stringify(request);
    }
    return options;
}

export async function call(api, method, request) {
    let options = createOptions(api, method, request);
    // 통신 에러 말고 자바스크립트 에러 처리 필요
    try {
        return await fetch(options.url, options)
            .then((response) => response.json()
                .then((json) => {
                    if (!response.ok || !response.status === 201) {
                        errorMsg = json.msg
                        return Promise.reject(json);
                    }
                    return json;
                })
            )
            .catch((error) => {
                console.log(error.status);
                if (error.status === 401) {
                    window.location.href = '/login';
                }
                return Promise.reject(error);
            });
    } catch (error) {
        alert(errorMsg);
    }
}

export async function login(userDTO) {
    let options = createOptions("/login", "POST", userDTO);

    try {
        return await fetch(options.url, options)
            .then((response) => {
                const head = response.headers.get('Authorization');
                response.json()
                .then((json) => {
                    if (!response.ok || !response.status === 201) {
                        errorMsg = json.msg
                        return Promise.reject(json);
                    } else {
                        // console.log("headers: ", [...response.headers]);
                        // alert("로그인 완료 : " + head);
                        window.location.href = "/";
                    }
                })
            }
            );
    } catch (error) {
        alert(errorMsg);
    }
}