import React, {useState, useCallback} from 'react';
import Form from 'react-bootstrap/Form';
import Container from 'react-bootstrap/Container';
import Button from 'react-bootstrap/Button'
import Col from 'react-bootstrap/Col';
import Row from 'react-bootstrap/Row';
import Card from 'react-bootstrap/Card';
import SignUpPopUp from './popUp/signUp';

import { AiFillEyeInvisible, AiFillEye } from 'react-icons/ai';
import {auth} from '../../services/authService';

export function LogIn ({isLoged}){
    
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [type, setType] = useState('password');
    const [showModal, setShowModal] = useState(false);

    const showHide = useCallback(() => {
        const newType = type === 'text' ? 'password' : 'text';
        setType(newType);
    });

    const handleSubmit = useCallback(async () => {       
        auth({
            username : username,
            password : password
        }).then(
            response => {
                if(response.status === 200){
                    console.log('Autenticacion correcta');
                    isLoged();
                }else{
                    console.log('Fallo autenticacion');
                }
            }
        );
    });

    const toggleSigIn = useCallback(() => {
        setShowModal(showModal => !showModal);
    });

    return (
        <div className='p-5 mb-100 bg-light text-black'>
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
                                    <Form.Group controlId="formBasicEmail">
                                        <Form.Label>Username</Form.Label>
                                        <Form.Control name="username" type="text" placeholder="Username" 
                                            value={username} 
                                            onChange={(event) => {setUsername(event.target.value)}}/>
                                        <Form.Text className="text-muted">
                                        </Form.Text>
                                    </Form.Group>
                                    <Form.Group controlId="formBasicPassword">
                                        <div className='_6lux'> 
                                        <Form.Label>Password</Form.Label>
                                        <Row>
                                            <Col xs={12} sm={11}>
                                                <input className="form-control form-control--rounded col-xs-2" name="password"
                                                        id="password" type={type} placeholder="Password" 
                                                        value={password}
                                                        onChange={(event) => {setPassword(event.target.value)}}/>
                                            </Col>
                                            <Col xs={12} sm={1} className="py-1">   
                                                <Button className="buttonHiden px-0" variant="outline-light" size="sm"
                                                    onClick={showHide}>
                                                    {
                                                        type === 'text'?<AiFillEye color='black'/>:<AiFillEyeInvisible color='black'/>
                                                    }
                                                </Button>
                                            </Col>
                                        </Row>
                                        </div>
                                    </Form.Group>
                                    <Row>
                                        <div className="d-grid gap-2" onClick={handleSubmit}>
                                            <Button variant="primary" >
                                                Log In
                                            </Button>
                                        </div>
                                        <div className="d-grid gap-2 pt-1" onClick={toggleSigIn}>
                                            <Button variant="primary" >
                                                Sign Up
                                            </Button>
                                        </div>
                                        <SignUpPopUp show={showModal} setUser={setUsername} handleClose={toggleSigIn} position="right center"/>
                                    </Row>                             
                                </Card.Body>
                            </Card>  
                        </Form>
                    </Col>   
                </Row>   
            </Container>
        </div>
    );
}

export default LogIn