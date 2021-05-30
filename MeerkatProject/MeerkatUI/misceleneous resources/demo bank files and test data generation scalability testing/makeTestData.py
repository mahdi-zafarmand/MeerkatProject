"""
*Timeframe
year one
*Vertices
1 {default=1.0;type=education;class=sector;SYS:X=0.4638264465072466;SYS:Y=0.30066391819036276;}

*Edges
1	2 {weight=7988.140481152276;type=Transfer}
*Arcs
*End
"""

def generate_vertices(no_of_vertices):
    for i in range(no_of_vertices):
        vertexLine = get_vertex_line(i)
        lines.append(vertexLine + '\n')
        
        
def get_vertex_line(vertex_no):
    vertex_line = str(vertex_no) + "\t" + "{" + "value=" + str(1) + ";}";
    return vertex_line;
    
def get_edge_line(vertex_no_from, vertex_no_from, edge_weight):
    edge_line = str(vertex_no_from) + "\t" + str(vertex_no_from) + '\s' + "{" + "weight=" + str(edge_weight) + ";}";
    return edge_line;
    
    
def generate_edges(no_of_vertices, no_of_edges):
    for i in range(no_of_edges):
        vertex_no_from, vertex_no_to = random.sample(range(1, no_of_vertices), 2)
        edge_weight = get_edge_weight()
        edge_line  = get_edge_line(vertex_no_from, vertex_no_from, edge_weight)
        lines.append(edge_line + '\n')
        
def get_edge_weight():
        weight = random.randint(1,100000)*random.random()
        return weight
    
def write_to_file(filename, linesArray):
    with open(filename, 'w') as f:
        for s in linesArray:
            f.write(s)
            
            
            
no_of_vertices = 50000;
no_of_edges = 80000;
filename = "testdata.meerkat"


lines = [];
lines.append("*Timeframe"  + '\n');
lines.append("year one" + '\n');
lines.append("*Vertices" + '\n');
generate_vertices(no_of_vertices);

lines.append("*Edges" + '\n');
generate_edges(no_of_edges);

lines.append("**Arcs" + '\n');
lines.append("*End");

write_to_file(filename,lines)

