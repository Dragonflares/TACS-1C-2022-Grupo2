import React, {Component} from 'react';
import Form from 'react-bootstrap/Form';
import Container from 'react-bootstrap/Container';
import Button from 'react-bootstrap/Button'
import Col from 'react-bootstrap/Col';
import Row from 'react-bootstrap/Row';

import {auth} from '../../services/authService';

export class LogIn extends Component {
    
    constructor(props){
        super(props);
        this.state = {
            username : '',
            password : '',
        };

    }

    handleChange= (event) => {
        const target = event.target;
        const value = target.value;
        const name = target.name;

        this.setState ({
            [name] : value,
        });
    }
    

    handleSubmit = async () => {
        const { username, password } = this.state;
       
        auth({
            username : username,
            password : password
        }).then(
            response => {
                if(response.status === 200){
                    console.log('Autenticacion correcta');
                }else{
                    console.log('Fallo autenticacion');
                }
            }
        );
    }


    render(){

        return (
            <Container>
                <Row>
                <Col md={{span: 6, offset:3}}>
                    <br/>
                    <h1>Log In</h1>
                    <br/>
                </Col>
                </Row>
                <Row>
                    <Col md={{span: 6, offset:3}}>
                    <Form>
                            <Form.Group controlId="formBasicEmail">
                                <Form.Label>User Name</Form.Label>
                                <Form.Control name="username" type="email" placeholder="Enter User Name" 
                                    value={this.state.username} 
                                    onChange={this.handleChange}/>
                                <Form.Text className="text-muted">
                                </Form.Text>
                            </Form.Group>
        
                            <Form.Group controlId="formBasicPassword">
                                <Form.Label>Password</Form.Label>
                                <Form.Control name="password" type="password" placeholder="Password" 
                                    value={this.state.password} 
                                    onChange={this.handleChange} />
                            </Form.Group>
                            
                            <Row>
                                <Form.Group as={Col} md={{span: 2, offset : 4}}>
                                        <Button variant="dark" type="button"  onClick={this.handleSubmit}>
                                            Log-in
                                        </Button>
                                </Form.Group>
                                <Form.Group as={Col} md={{span: 2, offset : 0}}>
                                        <Button variant="dark" type="button">
                                            Sign-Up
                                        </Button>
                                </Form.Group>              
                            </Row>    
                        </Form>
                    </Col>   
                </Row>   
            </Container>
        );
    }
}

export default LogIn