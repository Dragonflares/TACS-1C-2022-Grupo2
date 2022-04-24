import React, {Component} from 'react';
import Form from 'react-bootstrap/Form';
import Container from 'react-bootstrap/Container';
import Button from 'react-bootstrap/Button'
import Col from 'react-bootstrap/Col';
import Row from 'react-bootstrap/Row';
import Card from 'react-bootstrap/Card';
import SignUpPopUp from './popUp/SignUp';

import { AiFillEyeInvisible, AiFillEye } from 'react-icons/ai';
import {auth} from '../../services/authService';

export class LogIn extends Component {
    
    constructor(props){
        super(props);
        this.state = {
            username : '',
            password : '',
            type : 'password',
            showModal: false,
        };

    }

    showHide = () => {
        this.setState({
            type: this.state.type === 'text' ? 'password' : 'text'
        });
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

    toggleSigIn = () => {
        this.setState({showModal : !this.state.showModal});
    }

    render(){

        return (
            <div class='p-5 mb-100 bg-light text-black'>
                <Container fluid>
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
                                <Card>
                                    <Card.Body>
                                        <Card.Text> 
                                            <Form.Group controlId="formBasicEmail">
                                                <Form.Label>Username</Form.Label>
                                                <Form.Control name="username" type="text" placeholder="Username" 
                                                    value={this.state.username} 
                                                    onChange={this.handleChange}/>
                                                <Form.Text className="text-muted">
                                                </Form.Text>
                                            </Form.Group>
                                            <Form.Group controlId="formBasicPassword">
                                                <div className='_6lux'> 
                                                <Form.Label>Password</Form.Label>
                                                <Row>
                                                    <Col xs={12} sm={11}>
                                                        <input class="form-control form-control--rounded col-xs-2" name="password"
                                                             id="password" type={this.state.type} placeholder="Password" 
                                                             value={this.state.password} onChange={this.handleChange}/>
                                                    </Col>
                                                    <Col xs={12} sm={1} className="py-1">   
                                                        <Button className="buttonHiden px-0" variant="outline-light" size="sm"
                                                            onClick={this.showHide}>
                                                            {
                                                                this.state.type === 'text'?<AiFillEye color='black'/>:<AiFillEyeInvisible color='black'/>
                                                            }
                                                        </Button>
                                                    </Col>
                                                </Row>
                                                </div>
                                            </Form.Group>
                                            <Row>
                                                <div className="d-grid gap-2" onClick={this.handleSubmit}>
                                                    <Button variant="primary" >
                                                        Log In
                                                    </Button>
                                                </div>
                                                <div className="d-grid gap-2 pt-1" onClick={this.toggleSigIn}>
                                                    <Button variant="primary" >
                                                        Sign Up
                                                    </Button>
                                                </div>
                                                <SignUpPopUp show={this.state.showModal} handleClose={this.toggleSigIn} position="right center"/>
                                            </Row>
                                        </Card.Text>                                
                                    </Card.Body>
                                </Card>  
                            </Form>
                        </Col>   
                    </Row>   
                </Container>
            </div>
        );
    }
}

export default LogIn