import React from 'react';
import { TextField, Paper, Button, Grid } from '@material-ui/core';

class AddTodo extends React.Component {
    constructor(props) {
        super(props);
        this.state = { item: { title: "" } };
        this.add = props.add;
    }

    // 함수안에서 프로퍼티에 접근하려면 정의부터
    // setState를 이용해서 상태를 업데이트
    onInputChange = (e) => {
        const thisItem = this.state.item;
        thisItem.title = e.target.value;
        this.setState({ item: thisItem });
    }

    onButtonClick = () => {
        this.add(this.state.item);
        this.setState({ item: { title: "" } }) // 다시 타이틀 초기화
    }

    enterKeyEventHandler = (e) => {
        if ( e.key === 'Enter') {
            this.onButtonClick();
        }
    }

    // xs 는 가장 작은 뷰포트 크기, md 는 중간 뷰포트 크기 => 반응형 레이아웃
    // Grid 레이아웃 가져왔으므로 너비는 총 12를 나누어서 사용한다.
    // fullwidth 그리드 아이템이 최대의 너비를 가지도록한다.
    // 지정하지 않았을때 기본색 primary = 파랑, secondary = 녹색

    // 텍스트필드에 입력을 하면 함수호출 + setState로 컴포넌트를 변경된 부분만 다시 렌더링 한다.
    // 이때 value로 데이터를 넣어주지 않으면 입력한 값이 사라질 수도 있다.
    render() {
        return (
            <Paper style={{ margin: 16, padding: 16 }} elevation={2}>
                <Grid container>
                    <Grid xs={11} md={11} item style={{ paddingRight: 16 }}>
                        <TextField
                            placeholder='Add Todo here'
                            fullWidth
                            onChange={this.onInputChange}
                            value={this.state.item.title}
                            onKeyDown={this.enterKeyEventHandler}
                        />
                    </Grid>
                    <Grid xs={1} md={1} item>
                        <Button
                            fullWidth
                            color='secondary'
                            variant='outlined'
                            onClick={this.onButtonClick}
                        >
                            +
                        </Button>
                    </Grid>
                </Grid>
            </Paper>
        );
    }
}

export default AddTodo;