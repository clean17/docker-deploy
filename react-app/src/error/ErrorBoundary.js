import React from "react";

window.onerror = (event, source, lineno, colno, error) => {
    // event = message
    // source = 오류를 생성한 스크립트의 URL이 포함된 문자열입니다.
    // lineno : colno = 오류 줄 : 열
    // 오류 처리 작업 수행
    console.error(event, source, lineno, colno, error);
};


class ErrorBoundary extends React.Component {
    constructor(props) {
        super(props);
        this.state = { hasError: false };
    }

    static getDerivedStateFromError(error) { // throw 시점에 발동 -> setState

        // 다음 렌더링에서 폴백 UI가 보이도록 상태를 업데이트 합니다.
        return { hasError: true };
    }

    componentDidCatch(error, errorInfo) { // render 이후 sideEffect
        // 에러 리포팅 서비스에 에러를 기록할 수도 있습니다.
        // logErrorToMyService(error, errorInfo);

        this.setState({ hasError: true });
        // 에러 로깅 또는 오류 처리 작업 수행
        console.log("ErrorBoundary 테스트");
        console.error(error);
    }

    render() {
        if (this.state.hasError) {
            // 오류 발생 시 대체 UI 또는 오류 메시지를 반환
            return <div>Something went wrong.</div>;
        }
        return this.props.children;
    }
}

export default ErrorBoundary;

// 사용 예시
// <ErrorBoundary>
//    <App />
// </ErrorBoundary>
