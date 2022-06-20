import React, {  useCallback, useEffect, useReducer } from 'react'
import Form from 'react-bootstrap/Form';
import Container from 'react-bootstrap/Container';
import Col from 'react-bootstrap/Col';
import Row from 'react-bootstrap/Row';
import Card from 'react-bootstrap/Card';
import Button from 'react-bootstrap/Button';
import { getLanguages } from '../../services/languageService';
import { ToastContainer, toast } from 'react-toastify';

import { getMeaning } from '../../services/dictionaryService';

export function Dictionary(){

    const initialValues = {
        form: {
            language: 'ENGLISH',
            search: '',
        },
        result: {
            word: '',
            meaning: '' 
        },
        languages: []
    }

    const [state, dispatch] = useReducer(reducer, initialValues); 
    
    useEffect(() => {
        const init = () => {
            getLanguages().then(response => {
                dispatch({type: 'setLanguages', value: response.data.response.languages});
            }).catch(e => {
                toast.error(e.response.data.response.message);
            })
        }

        init();
    }, []);

    const handleChange = useCallback((e) => {
        const { name, value } = e.target;

        dispatch({type: 'setForm', prop: name, value: value});
    });

    const handleSubmit = useCallback((event) => {
        event.preventDefault();
        event.stopPropagation();

        getMeaning(state.form.search.toLowerCase(), state.form.language).then(response => {  
            
            dispatch({type: 'setResult', value: {
                word: state.form.search,
                meaning: response.data.response.phrase
            }})
            
        }).catch(e => {
            toast.error(e.response.data.response.message);
        })
    });
     

    return (
        <div className='p-5 mb-100 bg-light text-black'>
            <Col xs={12} md={{offset: 3, span: 6}}>
                <Container fluid>
                    <Card  className="py-2">
                        <Card.Body>
                            <Card.Title>Dictionary</Card.Title>
                            <Form onSubmit={handleSubmit}>
                                <Row>
                                    <Col xs={12} md={3} className="py-1">
                                        <Form.Group controlId="languageControl">
                                            <Form.Select name='language' 
                                                value={state.form.language} 
                                                onChange={handleChange}>
                                                {state.languages.map(lang => (
                                                    <option key={lang} value={lang}>{lang}</option>
                                                ))}
                                            </Form.Select>
                                        </Form.Group>
                                    </Col>
                                    <Col xs={12} md={7}  className="py-1">
                                        <Form.Group controlId="searchControl">
                                            <Form.Control type='text' name='search' placeholder='Word Search'
                                                value={state.form.search} 
                                                onChange={handleChange}/>                                                    
                                            <Form.Text className="text-muted"></Form.Text>
                                        </Form.Group>
                                    </Col>
                                    <Col md={2} className="py-1">
                                        <div className={"d-grid gap-2"}>
                                            <Button type="submit">Search</Button>
                                        </div>
                                    </Col>
                                </Row>
                            </Form>
                        </Card.Body>
                    </Card>
                    {
                        state.result.word !== ''?
                        <>
                            <Card  className="py-2">
                                <Card.Body>
                                    <Card.Title>
                                        {state.result.word}
                                    </Card.Title>
                                    <Card.Text>
                                        {state.result.meaning}
                                    </Card.Text>
                                </Card.Body>
                            </Card>
                        </>
                        :
                        <>
                        </>
                    }
                </Container>
            </Col>
            <ToastContainer/>
        </div>
    );
}

function reducer(state, action){
    switch(action.type){
        case 'setForm': return {
            ...state,
            form: {
                ...state.form,
                [action.prop]: action.value
            }
        };
        case 'setResult': return {
            ...state,
            result: action.value
        };
        case 'setLanguages': return {
            ...state,
            languages: action.value
        };
        default: throw new Error();
    }
}

export default Dictionary
