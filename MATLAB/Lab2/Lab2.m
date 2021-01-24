%% Run primal and dual problem
target_function_primal = -[13; 11];
A_primal = [4, 5; 5, 3; 1, 2];
b_primal = [1500; 1575; 420];
lower_bound_primal = [0; 0];

options = optimoptions('linprog', 'Algorithm', 'dual-simplex', 'Display', 'off');
[x_opt_primal, f_opt_primal, ~, ~ , ~] = linprog(target_function_primal, A_primal, b_primal, [], [], lower_bound_primal, [], [], options);
%_________________________________________________________________________________________________________________________________________

target_function_dual = [1500; 1575; 420];
A_dual = -[4, 5, 1; 5, 3, 2];
b_dual = -[13; 11];
lower_bound_dual = [0; 0; 0];

options = optimoptions('linprog', 'Algorithm', 'dual-simplex', 'Display', 'off');
[x_opt_dual, f_opt_dual, ~, ~ , ~] = linprog(target_function_dual, A_dual, b_dual, [], [], lower_bound_dual, [], [], options);


%% Run integer programming

target_function_intprog = ones(7,1);
A_intprog = - ... 
    [1, 0, 0, 1, 1, 1, 1;
    1, 1, 0, 0, 1, 1, 1;
    1, 1, 1, 0, 0, 1, 1;
    1, 1, 1, 1, 0, 0, 1;
    1, 1, 1, 1, 1, 0, 0;
    0, 1, 1, 1, 1, 1, 0;
    0, 0, 1, 1, 1, 1, 1;
    ];
b_intprog = -[8; 6; 5; 4; 6; 7; 9];
intcon = [1, 2, 3, 4, 5, 6, 7];
lb_intprog = [0; 0; 0; 0; 0; 0; 0];

options = optimoptions('intlinprog', 'Display', 'off');
[x_opt_intprog, f_opt_intprog, ~, ~] = intlinprog(target_function_intprog, intcon, A_intprog, b_intprog, [], [], lb_intprog, [], options);

options = optimoptions('linprog', 'Algorithm', 'dual-simplex', 'Display', 'off');
[x_opt_relaxed, f_opt_relaxed, ~, ~ , ~] = linprog(target_function_intprog, A_intprog, b_intprog, [], [], lb_intprog, [], options);


