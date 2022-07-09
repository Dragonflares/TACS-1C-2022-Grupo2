import React, { useCallback, useEffect, useReducer } from "react";
import { useParams } from "react-router-dom";
import { createTournament, updateTournament, getTournament, deleteTournament } from "../../../services/tournamentService";
import { useValidateMode } from "../../../shared/hooks/validateModeHook";
import { useValidateNumericId } from "../../../shared/hooks/validateNumericId";
import { 
    Tabs, Tab, Card, Container, Row, Button,
    Col, Form, InputGroup, FloatingLabel
} from "react-bootstrap";
import Participants from "./participants";
import { getPrivacies } from "../../../services/privacyService";
import { ToastContainer, toast } from 'react-toastify';
import { getLanguages } from "../../../services/languageService";

let tzoffset = (new Date()).getTimezoneOffset() * 60000;

const today = new Date(Date.now() - tzoffset).toISOString().slice(0,10);
const tomorrow = new Date(Date.now() - tzoffset);
tomorrow.setDate(tomorrow.getDate() + 1);
const pastTomorrow = new Date(Date.now() - tzoffset);
pastTomorrow.setDate(pastTomorrow.getDate() + 2);

const tomorrowS = tomorrow.toISOString().slice(0,10);
const pastTomorrowS = pastTomorrow.toISOString().slice(0,10);

const initialValues = {
    validated: false,
    privacies: [],
    languages: [],
    validDate: {},
    validName: {},
    form: {
        name: '',
        language: 'ENGLISH',
        startDate: today,
        endDate: tomorrowS,
        privacy: 'PRIVATE'
    }
}

export function Tournament ({redirectFromRoot}) {
    const {action, id} = useParams();

    const [state, dispatch] = useReducer(reducer, initialValues);

    useEffect(() => {
        const Init =  () => {
            const validMode = useValidateMode(action);

            if(!validMode){
                redirectFromRoot('error');
            }
            
            getLanguages().then(response => {
                dispatch({type: 'setLanguages', value: response.data.response.languages});
            }).catch(e => {
                toast.error(e.response.data.response.message);
            })

            getPrivacies().then(response => {
                dispatch({type: 'setPrivacies', value: response.data.response.privacys});
            }).catch(e => {
                toast.error(e.response.data.response.message);
            })

            if(action !== 'create'){

                // if(!useValidateNumericId(id)){
                //     redirectFromRoot('error');
                // }
    
                getTournament(id).then(
                    response => {
                            if(state.form.startDate < today){
                                redirectFromRoot('error');
                            }

                            dispatch({type: 'setTournament', value: {
                                name: response.data.response.name,
                                language: response.data.response.language,
                                startDate: response.data.response.startDate,
                                endDate: response.data.response.endDate,
                                privacy: response.data.response.privacy,
                            }});
                    }
                ).catch(e => {
                    toast.error(e.response.data.response.message);
                })
            }
        }

        Init();
    }, []);

    const handleTournamentChange = useCallback((e) =>{
        const { name, value } = e.target;
        
        dispatch({type: 'setForm', prop: name, value: value});
    });

    const handleSubmit = useCallback((event) => {       
        event.preventDefault();
        event.stopPropagation();

        if(!state.validated){
            dispatch({type: 'toggleValidated'});
        }

        if(state.form.name === '' || !state.form.startDate || !state.form.endDate)
        {
            toast.error("Fill required fields");

            return;
        }

        if(state.form.endDate < state.form.startDate || state.form.startDate < today){
            dispatch({type: 'toggleValidated'});
            dispatch({type: 'toggleValidateDateInput'});

            toast.error("Dates invalid!");
            return;
        }else{
            dispatch({type: 'toggleValidateDateInput'});
        }

        if(action  === 'create') {
            createTournament(state.form).then(response => {
                    redirectFromRoot(`tournament/edit/${response.data.response.id}`);
            }).catch(e => {
                toast.error(e.response.data.response.message);
            })
        }else{
            updateTournament(id, state.form).then(() => {
                    redirectFromRoot(`tournament/edit/${id}`);
            }).catch(e => { 
                toast.error(e.response.data.response.message);
            })
        }
    });

    const handleDelete  = useCallback(() => {
        deleteTournament(id).then(response => {
            redirectFromRoot(`tournaments`);                
        }).catch(e => {
            toast.error(e.response.data.response.message);
        })   
    });

    return(
        <Container>
            <Col md={{span: 6, offset: 3}} xs={12}>
                <Card>
                    <Card.Header>
                        <Card.Title>
                            {action.toUpperCase()} {action !== 'create' ? state.form.name : ''}
                        </Card.Title>    
                    </Card.Header>
                    <Card.Body>
                        <Tabs 
                            defaultActiveKey="data"
                            id="tournamentTabs"
                            className="mb-3"
                        >
                            <Tab eventKey={'data'} title={'Tournament Data'}>
                                <Form onSubmit={handleSubmit} noValidate validated={state.validated}>
                                    <Row>
                                        <Col md={6} xs={12}>
                                            <Form.Group className='_6lux' controlId="formTournamentName">
                                                <InputGroup>
                                                    <FloatingLabel className='group-first-element'>
                                                        <Form.Control name="name" type="text" placeholder="Name" required
                                                            readOnly={action === 'view' || action === 'delete'}
                                                            value={state.form.name} 
                                                            onChange={handleTournamentChange}
                                                            {...state.validName}/>
                                                        <Form.Text className="text-muted">
                                                        </Form.Text>
                                                        <label style={{paddingLeft:0, marginLeft: '1em'}}>Name</label>   
                                                    </FloatingLabel>
                                                </InputGroup>
                                            </Form.Group>
                                        </Col>
                                        <Col md={3} xs={12}>
                                            <Form.Group className='_6lux' controlId="formTournamentLang">
                                                <InputGroup>
                                                    <FloatingLabel className='group-first-element'>
                                                        {
                                                            action === 'create' || action === 'edit' ? 
                                                            <Form.Select 
                                                                name="language" required
                                                                readOnly={action === 'view' || action === 'delete'}
                                                                value={state.form.language} 
                                                                onChange={handleTournamentChange}>
                                                                    {state.languages.map(lang => (
                                                                         <option key={lang} value={lang}>{lang}</option>
                                                                    ))}
                                                            </Form.Select>
                                                            :
                                                            <>
                                                                <Form.Control 
                                                                    name="language" type="text"
                                                                    readOnly={true}
                                                                    value={state.form.language} />
                                                                <Form.Text className="text-muted">
                                                                </Form.Text>
                                                            </>
                                                        }
                                                        <label style={{paddingLeft:0, marginLeft: '1em'}}>Language</label>   
                                                    </FloatingLabel>
                                                </InputGroup>
                                            </Form.Group>
                                        </Col>
                                        <Col md={3} xs={12}>
                                            <Form.Group className='_6lux' controlId="formTournamentPrivacy">
                                                <InputGroup>
                                                    <FloatingLabel className='group-first-element'>                                                        
                                                        {
                                                            action === 'create' || action === 'edit' ? 
                                                            <Form.Select name="privacy" required
                                                                readOnly={action === 'view' || action === 'delete'}
                                                                value={state.form.privacy} 
                                                                onChange={handleTournamentChange}>
                                                                    {
                                                                        state.privacies.map(p => (
                                                                            <option key={p} value={p}>{p}</option>
                                                                        ))
                                                                    }
                                                            </Form.Select>
                                                            :
                                                            <>
                                                                <Form.Control 
                                                                    name="privacy" type="text"
                                                                    readOnly={true}
                                                                    value={state.form.privacy} />
                                                                <Form.Text className="text-muted">
                                                                </Form.Text>
                                                            </>
                                                        }
                                                        <label style={{paddingLeft:0, marginLeft: '1em'}}>Privacy</label>   
                                                    </FloatingLabel>
                                                </InputGroup>
                                            </Form.Group>
                                        </Col>
                                    </Row>
                                    <Row>
                                        <Col md={6} xs={12}>
                                            <Form.Group className='_6lux' controlId="formTournamentStart">
                                                <InputGroup>
                                                    <FloatingLabel className='group-first-element'>
                                                        <Form.Control name="startDate" type="date" placeholder="Start Date" required
                                                            readOnly={action === 'view' || action === 'delete'}
                                                            value={state.form.startDate}
                                                            onChange={handleTournamentChange}
                                                            {...state.validDate}/>
                                                        <label style={{paddingLeft:0, marginLeft: '1em'}}>Start Date</label>   
                                                    </FloatingLabel>
                                                </InputGroup>
                                            </Form.Group>
                                        </Col>                                
                                        <Col md={6} xs={12}>
                                            <Form.Group className='_6lux' controlId="formTournamentEnd">
                                                <InputGroup>
                                                    <FloatingLabel className='group-first-element'>
                                                        <Form.Control name="endDate" type="date" placeholder="End Date" required
                                                            readOnly={action === 'view' || action === 'delete'}
                                                            value={state.form.endDate}
                                                            onChange={handleTournamentChange}
                                                            {...state.validDate}/>
                                                        <label style={{paddingLeft:0, marginLeft: '1em'}}>End Date</label>   
                                                    </FloatingLabel>
                                                </InputGroup>
                                            </Form.Group>
                                        </Col>
                                    </Row>                         
                                    <Row>
                                        {
                                            action === 'delete' ?
                                            <Button
                                                style={{
                                                    margin: '1em 0 0 0 ',
                                                }} 
                                                variant={'danger'} 
                                                type={'button'}
                                                onClick={handleDelete}
                                            >
                                                Delete
                                            </Button>
                                            :
                                            action === 'view' ? 
                                            <></>
                                            :
                                            <Button
                                                style={{
                                                    margin: '1em 0 0 0 ',
                                                }}
                                                variant={'primary'}
                                                type={'submit'}                                                
                                            >
                                                Save
                                            </Button>
                                        }
                                    </Row>
                                </Form>
                            </Tab>
                            {
                                action !== 'create'  ? 
                                <Tab eventKey={'participants'} title={'Participants'}>
                                    <Participants action={action} id={id}/>
                                </Tab>
                                :
                                <></>
                            }
                        </Tabs>
                    </Card.Body>
                </Card>
            </Col>
            <ToastContainer/>
        </Container>        
    );
}

function reducer(state, action){
    switch(action.type){
        case 'setForm' : return {
            ...state,
            form: {
                ...state.form,
                [action.prop]: action.value
            }
        }
        case 'setTournament': return {
            ...state,
            form: action.value
        }
        case 'toggleValidated': return {
            ...state,
            validated: !state.validated
        }
        case 'toggleValidateDateInput': {
            if(state.validDate['isValid'] !== undefined){
                return {
                    ...state,
                    validDate: {},
                    validName: {}
                }
            }else{
                return {
                    ...state,
                    validDate: {
                        isValid: false,
                        isInvalid: true,
                    },
                    validName: {
                        isValid: true,
                        isInvalid: false,
                    }
                }
            }
        }
        case 'setPrivacies': return {
            ...state,
            privacies: action.value
        }
        case 'setLanguages': return {
            ...state,
            languages: action.value
        }
        default: throw new Error();
    }
}

export default Tournament;