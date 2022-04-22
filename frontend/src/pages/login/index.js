import React, {Component} from 'react';
import Form from 'react-bootstrap/Form';
import Container from 'react-bootstrap/Container';
import Button from 'react-bootstrap/Button'
import Col from 'react-bootstrap/Col';
import Row from 'react-bootstrap/Row';
import Card from 'react-bootstrap/Card';
import PopUpCustom from './popUp/PopUpCustom';

import { AiFillEyeInvisible, AiFillEye } from 'react-icons/ai';
import {auth} from '../../services/authService';

export class LogIn extends Component {
    
    constructor(props){
        super(props);
        this.state = {
            username : '',
            password : '',
            type : 'password'
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


    render(){

        return (
            <div class='p-5 mb-100 bg-light text-black'>
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
                        <Card>
                            <Card.Body>
                                <Card.Text> 
                                    <Form.Group controlId="formBasicEmail">
                                        <Form.Label>Username</Form.Label>
                                        <Form.Control name="username" type="email" placeholder="e-mail or phone number" 
                                            value={this.state.username} 
                                            onChange={this.handleChange}/>
                                        <Form.Text className="text-muted">
                                        </Form.Text>
                                    </Form.Group>
                                    <Form.Group controlId="formBasicPassword">
                                        <div className='_6lux'> 
                                        <Form.Label>Password</Form.Label>
                                        <Row>
                                            <Col sm={11}>
                                                <input class="form-control form-control--rounded col-xs-2" name="password" id="password" type={this.state.type} placeholder="password" value={this.state.password} onChange={this.handleChange}/>
                                            </Col>
                                            <Col sm={1}>   
                                                <Button className={'buttonHiden'} variant="outline-light" size="sm" onClick={this.showHide}>
                                                    {
                                                        this.state.type === 'text'?<AiFillEye color='black'/>:<AiFillEyeInvisible color='black'/>
                                                    }
                                                </Button>
                                            </Col>
                                        </Row>
                                        </div>                                        
                                        <div className="d-grid gap-2" onClick={this.handleSubmit}>
                                        <Button variant="primary" >
                                            Log In
                                        </Button>
                                        <div class="_6lux">
                                            <a href="https://www.facebook.com/recover/initiate/?privacy_mutation_token=eyJ0eXBlIjowLCJjcmVhdGlvbl90aW1lIjoxNjUwNjA0MTUwLCJjYWxsc2l0ZV9pZCI6MzgxMjI5MDc5NTc1OTQ2fQ%3D%3D&amp;ars=facebook_login">
                                                Forgot password?</a>
                                        </div>
                                        </div>
                                        <hr/>
                                        <PopUpCustom position="right center"/>
                                    </Form.Group>
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