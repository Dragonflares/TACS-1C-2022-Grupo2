import React, {useState, useCallback, useReducer} from 'react';
import Form from 'react-bootstrap/Form';
import Container from 'react-bootstrap/Container';
import Button from 'react-bootstrap/Button'
import Col from 'react-bootstrap/Col';
import Row from 'react-bootstrap/Row';
import Card from 'react-bootstrap/Card';
import SignUpPopUp from './popUp/signUp';
import InputGroup from 'react-bootstrap/InputGroup';
import FloatingLabel from 'react-bootstrap/FloatingLabel';
import { ToastContainer, toast } from 'react-toastify';

import { AiFillEyeInvisible, AiFillEye } from 'react-icons/ai';
import {auth, isAuthenticated} from '../../services/authService';

const initialValues = {
    form: {
        username: '',
        password: '',
    },
    type: 'password',
    showModal: false,
    validated: false,
}

export function LogIn ({isLoged}){

    const [state, dispatch] = useReducer(reducer, initialValues);


    const showHide = useCallback(() => {
        dispatch({type: 'toggleType'});
    });

    const handleChange = useCallback((e) => {
        const { name, value } = e.target;

        dispatch({type: 'setForm', prop: name, value: value});
    });

    const handleSubmit = useCallback((event) => {       
        event.preventDefault();
        event.stopPropagation();
    
        if(!state.validated){
            dispatch({type: 'toggleValidated'});
        }
    
        if(!state.form.username || !state.form.password || state.form.username === '' || state.form.password === '')
          return;  

        auth(state.form).then(() => {
                isLoged();
            }
        ).catch(e => {
            toast.error(e.response.data.response.message);
        })
    });

    const toggleSigIn = useCallback(() => {
        dispatch({type: 'toggleModal'});
    });

    const handleOnSuccess = useCallback(() => {
        toast.success("User successfully created! Now you can login.");
    })

    const handleOnError = useCallback((e) => {
        toast.error(e.response.data.response.message);
    })

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
                        <Card>
                            {
                                isAuthenticated() ? 
                                <Card.Body>
                                    <Card.Text><h3>Already Logged In</h3></Card.Text>
                                </Card.Body>
                                :
                                <Form onSubmit={handleSubmit} noValidate validated={state.validated}>
                                    <Card.Body>
                                        <Form.Group as={Row} className='_6lux' controlId="formUsername">
                                            <InputGroup>
                                                <FloatingLabel className='group-first-element'>
                                                    <Form.Control name="username" type="text" placeholder="Username" required
                                                        value={state.form.username} 
                                                        onChange={handleChange}/>
                                                    <Form.Text className="text-muted">
                                                    </Form.Text>
                                                    <label style={{paddingLeft:0, marginLeft: '1em'}}>UserName</label>   
                                                </FloatingLabel>
                                            </InputGroup>
                                        </Form.Group>
                                        <Form.Group as={Row} className='_6lux' controlId="formPassword">
                                            <InputGroup>
                                                <FloatingLabel className='group-first-element'> 
                                                    <Form.Control className="form-control-rounded" 
                                                        name="password" required
                                                        id="password" type={state.type} placeholder="Password" 
                                                        value={state.form.password}                                                    
                                                        onChange={handleChange}/>                                               
                                                    <label style={{paddingLeft:0, marginLeft: '1em'}}>Password</label>   
                                                </FloatingLabel>
                                                <Button variant="outline-secondary"
                                                        onClick={showHide}
                                                        size="sm">
                                                        {
                                                            state.type === 'text'?<AiFillEye color='black'/>:<AiFillEyeInvisible color='black'/>
                                                        }
                                                </Button>
                                            </InputGroup>
                                        </Form.Group>
                                        <Row className='_6lux'>
                                            <div className="d-grid gap-2">
                                                <Button variant="primary" type='submit'>
                                                    Log In
                                                </Button>
                                            </div>
                                            <div className="d-grid gap-2 pt-1" onClick={toggleSigIn}>
                                                <Button variant="primary" >
                                                    Sign Up
                                                </Button>
                                            </div>
                                            <SignUpPopUp show={state.showModal} onSetUser={(val) => {dispatch({type: 'setUsername', value: val})}} 
                                            onClose={toggleSigIn} position="right center" onError={handleOnError} onSuccess={handleOnSuccess}/>
                                        </Row>                             
                                    </Card.Body>
                                </Form>
                            }
                        </Card>  
                    </Col>   
                </Row>
            </Container>
            <ToastContainer/>   
        </div>
    );
}

function reducer (state, action){
    switch(action.type){
        case 'setForm' : return {
            ...state,
            form: {
                ...state.form,
                [action.prop]: action.value
            }
        };
        case 'setUsername': return {
            ...state,
            form: {
                ...state.form,
                username: action.value
            }
        }
        case 'toggleType' : {
            const newType = state.type === 'text' ? 'password' : 'text';
            return {
                ...state,
                type: newType
            };
        }
        case 'toggleModal': return {
            ...state,
            showModal: !state.showModal
        };
        case 'toggleValidated': return {
            ...state,
            validated: !state.validated
        }

        default: throw new Error();
    }
}

export default LogIn