import React, {useState, useCallback, useEffect} from "react";
import Card from 'react-bootstrap/Card';
import Container from 'react-bootstrap/Container';
import Col from 'react-bootstrap/Col';
import { PaginatedTable } from "../../shared/components/paginated-table";
import { Row } from "react-bootstrap";

export default function Positions(){
    const pageSize = 10;
    const [data, setData] = useState({
        elements: [],
        count: 10,
    });
    
    const headings = [
        {   
            name: 'position',
            show: '#'
        },
        {
            name: 'name',
            show: 'Name'
        },
        {
            name: 'points',
            show: 'Points',
        }
    ];

    const getData = async (page, pageSize) =>  {
        

        const offset = ((page - 1) * pageSize);
        /*
        await getPublicTournaments(offset, pageSize).then(
            response => {//HAY QUE CAMBIAR LA RESPUESTA
                if(response.status === 200){                    
                   setData(response.data);
                }else{

                }
            }
        );*/
        
        const elements = [];
        for(let i = offset + 1; i < offset + pageSize + 1; i++){
            elements.push({
                position: i,
                name: `USER ${i}`,
                points: 0,
            });
        }

        const mock = {
            elements : elements,
            count : 20,
        }

        setData(mock);
    };

    useEffect(() => {
        const init = async () => {
            await getData(1, pageSize);
        };

        init();
    }, []);

    const handlePageChange = useCallback(async (page, pageSize) => {
        await getData(page, pageSize);
    });

    const handleRowClick = useCallback((element) => {
    });

    return (
        <>
            <Col xs={12} md={{offset: 3, span: 6}}>
                <Container fluid>
                    <Card>
                        <Card.Body>
                            <Card.Title>My Tournaments</Card.Title>
                            <Row>
                                <PaginatedTable 
                                    headings={headings}
                                    data={data}
                                    pageSize={pageSize}
                                    handlePageChange={handlePageChange}
                                    onClick={handleRowClick}
                                    hover={false}
                                    key='positions'
                                />   
                            </Row>                                                     
                        </Card.Body>
                    </Card>
                </Container>
            </Col>
        </>
    )
}
