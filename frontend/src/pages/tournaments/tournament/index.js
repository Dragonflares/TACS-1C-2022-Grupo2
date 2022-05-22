import React, { useCallback, useEffect, useState } from "react";
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

const englishLang = 'ENGLISH';
const spanishLang = 'SPANISH';

export function Tournament ({redirectFromRoot}) {
    const {action, id} = useParams();
    const today = new Date();
    const tomorrow = new Date();
    tomorrow.setDate(tomorrow.getDate() + 1);
    const [validated, setValidated] = useState(false);
    const [privacies, setPrivacies] = useState([]);
    const [validDate, setValidDate] = useState({
    });
    const [validName, setValidName] = useState({});
    const [tournament, setTournament] = useState({
        name: '',
        language: englishLang,
        startDate: today.toISOString().slice(0,10),
        endDate: tomorrow.toISOString().slice(0,10),
        privacy: 'PRIVATE'
    });

    useEffect(() => {
        const init =  () => {
            if(!useValidateMode(action)){
                redirectFromRoot('error');
            }
            
            getPrivacies().then(response => {
                if(response.status === 200){
                    setPrivacies(response.data.response);
                }
            });

            if(action !== 'create'){
                if(!useValidateNumericId(id)){
                    redirectFromRoot('error');
                }
    
                getTournament(id).then(
                    response => {
                        if(response.status === 200){
                            setTournament({
                                name: response.data.response.name,
                                language: response.data.response.language,
                                startDate: response.data.response.startDate,
                                endDate: response.data.response.endDate,
                                privacy: response.data.response.privacy,
                            });
                        }else{
                            redirectFromRoot('error');
                        }
                    }
                );
            }
        }

        init();
    }, []);

    const handleTournamentChange = useCallback((e) =>{
        const { name, value } = e.target;
        setTournament(prevState => ({
            ...prevState,
            [name]: value
        }));
    });

    const handleSubmit = useCallback((event) => {       
        event.preventDefault();
        event.stopPropagation();

        if(!validated){
            setValidated(validated => !validated);
        }

        if(tournament.name === '' || !tournament.startDate || !tournament.endDate)
        {
            toast.error("Fill required fields");

            return;
        }

        if(tournament.endDate < tournament.startDate || tournament.startDate < today.toISOString().slice(0,10)){
            setValidated(false);
            setValidName({
                isValid: true,
                isInvalid: false,
            });
            setValidDate({
                isValid: false,
                isInvalid: true,
            });
            toast.error("Dates invalid!");
            return;
        }else{
            setValidName({
            });
            setValidDate({
            });
        }

        if(action  === 'create') {
            createTournament(tournament).then(response => {
                    redirectFromRoot(`tournament/view/${response.data.response.id}`);
            }).catch(e => 
                toast.error(e.response.data.response.message));
        }else{
            updateTournament(id, tournament).then(() => {
                    redirectFromRoot(`tournament/view/${id}`);
            }).catch(e => 
                toast.error(e.response.data.response.message));
        }
    });

    const handleDelete  = useCallback(() => {
        deleteTournament(id).then(response => {
            if(response.status === 200){
                redirectFromRoot(`tournaments`);                
            }else{
                redirectFromRoot('error');
            }
        })   
    });

    return(
        <Container>
            <Col md={{span: 6, offset: 3}} xs={12}>
                <Card>
                    <Card.Header>
                        <Card.Title>
                            {action.toUpperCase()} {action !== 'create' ? tournament.name : ''}
                        </Card.Title>    
                    </Card.Header>
                    <Card.Body>
                        <Tabs 
                            defaultActiveKey="data"
                            id="tournamentTabs"
                            className="mb-3"
                        >
                            <Tab eventKey={'data'} title={'Tournament Data'}>
                                <Form onSubmit={handleSubmit} noValidate validated={validated}>
                                    <Row>
                                        <Col md={6} xs={12}>
                                            <Form.Group className='_6lux' controlId="formTournamentName">
                                                <InputGroup>
                                                    <FloatingLabel className='group-first-element'>
                                                        <Form.Control name="name" type="text" placeholder="Name" required
                                                            readOnly={action === 'view' || action === 'delete'}
                                                            value={tournament.name} 
                                                            onChange={handleTournamentChange}
                                                            {...validName}/>
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
                                                                value={tournament.language} 
                                                                onChange={handleTournamentChange}>
                                                                    <option value={englishLang}>ENGLISH</option>
                                                                    <option value={spanishLang}>ESPAÑOL</option>
                                                            </Form.Select>
                                                            :
                                                            <>
                                                                <Form.Control 
                                                                    name="language" type="text"
                                                                    readOnly={true}
                                                                    value={tournament.language} />
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
                                                                value={tournament.privacy} 
                                                                onChange={handleTournamentChange}>
                                                                    {
                                                                        privacies.map(p => (
                                                                            <option key={p.id} value={p.id}>{p.desc}</option>
                                                                        ))
                                                                    }
                                                            </Form.Select>
                                                            :
                                                            <>
                                                                <Form.Control 
                                                                    name="privacy" type="text"
                                                                    readOnly={true}
                                                                    value={tournament.privacy} />
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
                                                            value={tournament.startDate}
                                                            onChange={handleTournamentChange}
                                                            {...validDate}/>
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
                                                            value={tournament.endDate}
                                                            onChange={handleTournamentChange}
                                                            {...validDate}/>
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

export default Tournament;