import React from 'react';
import { TextField, Paper, Button, Grid } from '@material-ui/core';

class AddTodo extends React.Component {
    constructor(props) {
        super(props);
        this.state = { item: { title: "" } };
    }

    // xs 는 가장 작은 뷰포트 크기, md 는 중간 뷰포트 크기 => 반응형 레이아웃
    // Grid 레이아웃 가져왔으므로 너비는 총 12를 나누어서 사용한다.
    // fullwidth 그리드 아이템이 최대의 너비를 가지도록한다.
    // 지정하지 않았을때 기본색 primary = 파랑, secondary = 녹색
    render() {
        return (
            <Paper style={{ margin: 16, padding: 16 }} elevation={ 2 }>
                <Grid container>
                    <Grid xs={11} md={11} item style={{ paddingRight: 16 }}>
                        <TextField placeholder='Add Todo here' fullWidth />
                    </Grid>
                    <Grid xs={1} md={1} item>
                        <Button fullWidth color='secondary' variant='outlined'>
                            +
                        </Button>
                    </Grid>
                </Grid>
            </Paper>
        );
    }
}

export default AddTodo;