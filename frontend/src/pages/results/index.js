import React, {useReducer, useCallback, useEffect} from "react";
import Form from 'react-bootstrap/Form';
import Container from 'react-bootstrap/Container';
import Col from 'react-bootstrap/Col';
import Row from 'react-bootstrap/Row';
import Card from 'react-bootstrap/Card';
import Button from 'react-bootstrap/Button';
import { getLanguages } from "../../services/languageService";
import { getDailyResults, createDailyResults, getUserDataStruct } from "../../services/userService";
import { ToastContainer, toast } from 'react-toastify';


const initialValues = {
    form: {
        language: 'ENGLISH',
        result: 0,
    },
    validated: false,
    results: [],
    languages: []
}

export function Results () {
    const [state, dispatch] = useReducer(reducer, initialValues);

    const Init = () => {
        getLanguages().then(response => {
            dispatch({type: 'setLanguages', value: response.data.response.languages});
        }).catch(e => {
            toast.error(e.response.data.response.message);
        })

        /*
        getDailyResults().then(response => {
            if(!response.data){
                dispatch({type: 'setResults', value: response.data.response.results});
            }
        }).catch(e => {
            toast.error(e.response.data.response.message);
        })
        */
    };

    useEffect(() => {
        Init();
    }, []);

    const handleSubmit = useCallback((event) => {       
        event.preventDefault();
        event.stopPropagation();

        if(!state.validated){
            dispatch({type: 'toggleValidated'});
        }

        if(!state.form.language || !state.form.result || state.form.language === '') {
          return;
        }

        let tzoffset = (new Date()).getTimezoneOffset() * 60000;

        createDailyResults({
            language: state.form.language,
            date: (new Date(Date.now() - tzoffset)).toISOString().slice(0, 10),
            points: state.form.result
        }).then(() => {
            toast.success("result created");
            Init();
        }).catch( e => {
            toast.error(e.response.data.response.message);
        })
    });

    const handleLangChange = useCallback((event) => {
        const  value = event.target.value;
        if(state.results.length > 0) {
            const langResult = state.results.find((r) => (r.lang === value));
            dispatch({type: 'setResult', value: langResult});
        } 
        
        dispatch({type: 'setValidated', value: false});
        dispatch({type: 'setLanguage', value: value});
    });

    return(
        <div className='p-5 mb-100 bg-light text-black'>
                <Col xs={12} md={{offset: 3, span: 6}}>
                    <Container fluid>
                        <Card  className="py-2">
                            <Card.Body>
                                <Card.Title>Add Result</Card.Title>
                                <Form onSubmit={handleSubmit} noValidate validated={state.validated}>
                                    <Row>
                                        <Col xs={12} md={3} className="py-1">
                                            <Form.Group controlId="languageControl">
                                                <Form.Select name='language' 
                                                    value={state.form.language} 
                                                    onChange={handleLangChange}>
                                                    {state.languages.map(lang => (
                                                        <option key={lang} value={lang}>{lang}</option>
                                                    ))}
                                                </Form.Select>
                                            </Form.Group>
                                        </Col>
                                        <Col xs={12} md={7}  className="py-1">
                                            <Form.Group controlId="searchControl">
                                                <Form.Control type='number' name='result' placeholder='Result'
                                                    value={state.result} min={0} max={7}
                                                    onChange={(event) => {dispatch({type: 'setResult', value:event.target.value})}}/>
                                                <Form.Text className="text-muted"></Form.Text>
                                            </Form.Group>
                                        </Col>
                                        <Col md={2} className="py-1">
                                            <div className={"d-grid gap-2"}>
                                                <Button type="submit">Add</Button>
                                            </div>
                                        </Col>
                                    </Row>
                                </Form>
                            </Card.Body>
                        </Card>
                    </Container>
                    <ToastContainer/>
                </Col>
            </div>
    );
}

function reducer(state, action){
    switch(action.type){
        case 'setLanguage': return {
            ...state,
            form: {
                ...state.form,
                language: action.value
            }
        }
        case 'setResult': return {
            ...state,
            form: {
                ...state.form,
                result: action.value
            }
        }
        case 'setResults': return {
            ...state,
            results: action.value
        }
        case 'toggleValidated': return {
            ...state,
            validated: !state.validated
        }
        case 'setValidated': return {
            ...state,
            validated: action.value
        }
        case 'setLanguages': return {
            ...state,
            languages: action.value
        }
        default: throw new Error();
    }
}

export default Results