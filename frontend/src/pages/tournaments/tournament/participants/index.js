import React, { useCallback, useEffect, useReducer } from "react";
import { Button, Form, InputGroup, Row, FloatingLabel } from 'react-bootstrap';
import { Input } from "react-bootstrap-typeahead";
import { addParticipants, getParticipants, getPositions } from "../../../../services/tournamentService";
import { PaginatedTable } from "../../../../shared/components/paginatedTable";
import { ToastContainer, toast } from 'react-toastify';
import NonPaginatedTable from "../../../../shared/components/table";

const initialValues = {
    participant: '',
    data: {
        elements: []
    },
    validated: false,
}

export default function Participants({id , action}){
    const headings = [
        {   
            name: 'name',
            show: 'Name'
        }
    ];

    const [state, dispatch] = useReducer(reducer, initialValues);

    const getData = () =>  {
        getPositions(id).then(
            response => {
                dispatch({type: 'setData', value: {
                    elements: Array.from(response.data.response).map((p, i) => (
                        {
                            id: i,
                            name: p.user.username,
                        }
                    )),
                }})
            }
        ).catch( e => {
                toast.error(e.response.data.response.message);
            })
    };

    useEffect(() => {
        const Init = () => {
            getData();
        };
        Init();
    }, []);


    const handleRowClick = useCallback((element) => {        
    });

    const handleSubmit = useCallback((event) => {
        event.preventDefault();
        event.stopPropagation();
    
        if(!state.validated){
            dispatch({type: 'toggleValidated'});
        }
        
        if(state.participant === ''){
            toast.error('Complete fields');
            return;
        }

        addParticipants(id, {username: state.participant}).then(
            response => {
                getData();
                toast.success("Successfully added participant!")
            }
        ).catch( e => {
                toast.error(e.response.data.response.message);
            })
    });

    return(
        <>
            {
                action === 'edit' ? 
                <Row>
                    <Form onSubmit={handleSubmit} noValidate validated={state.validated}>
                        <Form.Group className='_6lux' controlId="formParticipantAdd">
                            <InputGroup>
                                <FloatingLabel className='group-first-element'>
                                            <Form.Control name="participant" type="text" placeholder="Add participant" required
                                                value={state.participant} 
                                                onChange={(event) => {dispatch({type: 'setParticipant', value: event.target.value})}}/>
                                            <Form.Text className="text-muted">
                                            </Form.Text>
                                        <label style={{paddingLeft:0, marginLeft: '1em'}}>Add participant</label>   
                                </FloatingLabel>
                                <Button type={'submit'}>Add</Button>
                            </InputGroup>
                        </Form.Group>
                    </Form>
                </Row>
                :<></>
            }
            {
                state.data.elements.length > 0 ? 
                <NonPaginatedTable 
                    data={state.data}
                    headings={headings}
                    onClick={handleRowClick}
                    hover={false}
                    key={'participants-table'}
                />
                :
                <h3>
                    NO SIGNED PARTICIPANTS
                </h3>
            }
            <ToastContainer/>
        </>
    );
}

function reducer(state, action){
    switch(action.type){
        case 'setData' : return{
            ...state,
            data: action.value
        }
        case 'setParticipant' : return{
            ...state,
            participant: action.value
        }       
        case 'toggleValidated': return {
            ...state,
            validated: !state.validated
        }
        default: throw new Error();
    }
}